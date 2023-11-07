import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { BaseCrudComponent } from '@shared/components/base-crud/base-crud.component';
import { CategoryAdminDataSource } from '../../services/category-admin.datasource';
import { DialogService } from '@shared/modules/dialog/dialog.service';
import { NotificationService } from '@shared/services/common/notificaton.service';
import { Category } from '@shared/models/blog';
import { concatMap, filter, finalize, switchMap, tap } from 'rxjs';
import { AdminCreateCategoryFormComponent } from '../../components/admin-create-category-form/admin-create-category-form.component';
import { AdminSearchCategoryFormComponent } from '../../components/admin-search-category-form/admin-search-category-form.component';

@Component({
  selector: 'app-list-admin-category',
  templateUrl: './list-admin-category.component.html',
  styleUrls: ['./list-admin-category.component.scss'],
})
export class ListAdminCategoryComponent
  extends BaseCrudComponent<CategoryAdminDataSource>
  implements OnInit, AfterViewInit
{
  totalRows = 0;
  currentPage = 0;

  listCategory: Category[] = [];

  @ViewChild(AdminSearchCategoryFormComponent)
  adminSearchCategoryForm!: AdminSearchCategoryFormComponent;

  constructor(
    public categoryAdminDataSource: CategoryAdminDataSource,
    protected override dialogService: DialogService,
    protected override notificationService: NotificationService
  ) {
    super(dialogService, categoryAdminDataSource, notificationService);
  }

  ngOnInit(): void {
    this.categoryAdminDataSource.loadData({
      page: this.currentPage,
      size: 10,
    });
    this.categoryAdminDataSource.dataSubject
      .pipe(filter((data) => !!data.content))
      .subscribe((res) => {
        this.listCategory = res.content;
        this.totalRows = res.totalElements;
      });
  }

  ngAfterViewInit(): void {
    this.adminSearchCategoryForm.emitSearch
      .pipe(
        tap((value) => {
          this.currentPage = 0;
          this.categoryAdminDataSource.loadData({
            ...value,
            page: this.currentPage,
          });
        })
      )
      .subscribe();

    this.adminSearchCategoryForm.emitCreate
      .pipe(
        switchMap(() =>
          this.openCreate(AdminCreateCategoryFormComponent, {
            header: 'Create quiz',
            data: {}
          })
        ),
        switchMap((formValue) => this.handleCreateOrUpdate(formValue))
      )
      .subscribe();
  }

  deleteCategory(category: Category): void {
    this.openDelete(
      'Confirm delete category?',
      'Are you sure you want to delete this category?',
      category
    ).subscribe();
  }

  updateCategory(category: Category): void {
    this.openUpdate(AdminCreateCategoryFormComponent, {
      header: 'Update category',
      data: category,
    })
      .pipe(concatMap((formValue) => this.handleCreateOrUpdate(formValue)))
      .subscribe();
  }

  recoverCategory(category: Category): void {
    this.openConfirm(
      'Confirm recover category?',
      'Are you sure you want to recover this category?',
      category
    )
      .pipe(
        concatMap(() =>
          this.categoryAdminDataSource.recoverCategory(category.id)
        ),
        finalize(() =>
          this.categoryAdminDataSource.loadData({ page: 0, size: 10 })
        )
      )
      .subscribe();
  }
}
