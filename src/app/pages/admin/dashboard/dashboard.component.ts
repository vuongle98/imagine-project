import { Component } from '@angular/core';
import { StatisticService } from '@shared/services/rest-api/statistic/statistic.service';
import { ChartConfiguration, ChartData, ChartEvent, ChartType } from 'chart.js';
import { Observable, tap } from 'rxjs';
import DataLabelsPlugin from 'chartjs-plugin-datalabels';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent {
  statisticData$ = new Observable<any>();

  public chartType: ChartType = 'bar';
  public chartPlugins = [DataLabelsPlugin];
  public chartOptions: ChartConfiguration['options'] = {
    responsive: true,
    // We use these empty structures as placeholders for dynamic theming.
    scales: {
      x: {},
      y: {
        min: 10,
      },
    },
    plugins: {
      legend: {
        display: true,
      },
      datalabels: {
        anchor: 'end',
        align: 'end',
      },
    },
  };

  constructor(private statisticService: StatisticService) {
    this.statisticData$ = this.statisticService.getStatistic();
  }

  ngOnInit(): void {}

  initChart(data: any): ChartData<'bar'> {
    return {
      labels: ['2006', '2007', '2008', '2009', '2010', '2011', '2012'],
      datasets: [
        { data: [65, 59, 80, 81, 56, 55, 40], label: 'Series A' },
        { data: [28, 48, 40, 19, 86, 27, 90], label: 'Series B' },
      ],
    };
  }
}
