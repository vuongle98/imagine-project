import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CustomHttpParamEncoder } from './custom-http-param-encoder';
import { Pageable } from '../../models/utils';

export interface QueryParams {
  [key: string]:
    | string
    | string[]
    | number
    | number[]
    | boolean
    | Boolean
    | null
    | Date
    | any;
}

export interface RequestParamsConfig {
  pathParams?: { [key: string]: string };
  queryParams?: QueryParams;
  responseType?: 'json';
  requestBody?: {
    type: 'application/json' | 'multipart/form-data';
    data?: any;
  };
  ocrBody?: FormData;
  headers?: {
    Accept?: 'image/svg+xml';
  };
}

export abstract class AbstractService {
  abstract apiEndpoint?: { [key: string]: string };

  protected constructor(protected http: HttpClient) {}

  /**
   * Dùng để gọi GET request và nhận về response json
   * @param endpoint API endpoint cần gọi GET request
   * @param params Danh sách params cần truyền vào request (nếu có), bao gồm pathParams và queryParams
   */
  protected get<T>(
    endpoint: string,
    params: Omit<RequestParamsConfig, 'requestBody'>,
    withHeader?: boolean
  ): Observable<T> {
    const url = this.buildApiUrl(endpoint, params.pathParams);

    const qParams = params.queryParams
      ? this.buildQueryParam(params.queryParams)
      : null;

    const responseType = params.responseType ? params.responseType : 'json';

    const options = qParams
      ? { params: qParams, responseType: responseType as 'json' }
      : { responseType: responseType as 'json' };

    const headers = params.headers ? params.headers : {};

    return this.http.get<T>(url, {
      observe: withHeader ? ('response' as 'body') : 'body',
      ...options,
      ...{ headers },
    });
  }

  /**
   * Dùng để gọi POST request
   * @param endpoint API endpoint cần gọi POST request
   * @param params Danh sách params cần truyền vào request (nếu có), bao gồm pathParams, queryParams và requestBody
   */
  protected post<T>(
    endpoint: string,
    params: RequestParamsConfig = {}
  ): Observable<T> {
    const url = this.buildApiUrl(endpoint, params.pathParams);

    const requestBody = this.buildRequestBody(params.requestBody);
    const qParams = this.buildQueryParam(params.queryParams);

    let options = { params: qParams, responseType: params.responseType };

    return this.http.post<T>(url, requestBody, options);
  }

  private buildQueryParam(queryParams: any): HttpParams {
    let result: HttpParams = new HttpParams({
      encoder: new CustomHttpParamEncoder(),
    });

    for (const property in queryParams) {
      if (
        queryParams.hasOwnProperty(property) &&
        queryParams[property] !== null &&
        queryParams[property] !== undefined &&
        queryParams[property] !== ''
      ) {
        result = result.set(property, queryParams[property].toString());
      }
    }
    return result;
  }

  /**
   * Tạo multipart/form-data từ requestBody params
   * @param requestBody Danh sách các requestBody params dùng để tạo form data
   * @return multipart/form-data
   */
  buildRequestBody(
    requestBody: RequestParamsConfig['requestBody']
  ): FormData | any {
    if (!requestBody) return {};

    // If type is application/json
    if (requestBody.type === 'application/json') {
      const result: { [key: string]: any } = {};

      if (Array.isArray(requestBody.data)) {
        return requestBody.data;
      } else {
        for (const property in requestBody.data) {
          if (requestBody.data.hasOwnProperty(property)) {
            if (requestBody.data[property] === null) {
              result[property] = null;
            } else if (
              requestBody.data[property] !== null &&
              requestBody.data[property] !== undefined
            ) {
              result[property] = requestBody.data[property];
            } else {
              result[property] = '';
            }
          }
        }
      }

      return result;
    }

    // If type is multipart/form-data
    const formData: FormData = new FormData();
    for (const property in requestBody.data) {
      if (
        requestBody.data.hasOwnProperty(property) &&
        requestBody.data[property] !== null
      ) {
        if (property === 'files') {
          for (const file of requestBody.data[property]) {
            formData.append(property, file);
          }
        } else {
          const value = requestBody.data[property];
          if (typeof value === 'object') {
            formData.append(
              property,
              JSON.stringify(requestBody.data[property])
            );
          } else {
            formData.append(property, requestBody.data[property]);
          }
        }
      }
    }
    return formData;
  }

  private buildApiUrl(
    endpoint: string,
    pathParams?: { [key: string]: string }
  ): string {
    let baseURL = 'http://localhost:8080/';

    if (pathParams) {
      endpoint = this.buildPathParams(endpoint, pathParams);
    }
    return baseURL + endpoint;
  }

  /**
   * Gắn path params lên API endpoint
   * @param endpoint Endpoint cần gắn path params
   * @param pathParams Danh sách các path params của endpoint này
   * @return Endpoint đã gắn path params
   */
  private buildPathParams(
    endpoint: string,
    pathParams?: { [key: string]: string }
  ): string {
    if (!pathParams) return '';

    Object.keys(pathParams).forEach((key) => {
      if (endpoint.indexOf(`{${key}`) !== -1) {
        endpoint = endpoint.replace(
          new RegExp(`\{${key}\}`, 'g'),
          pathParams[key]
        );
      }
    });
    return endpoint;
  }
}
