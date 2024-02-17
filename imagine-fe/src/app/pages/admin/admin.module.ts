import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { DashboardComponent } from './dashboard/dashboard.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { NgChartsModule } from 'ng2-charts';
import { ChartDisplayComponent } from './dashboard/components/chart-display/chart-display.component';
import { StatisticCardComponent } from './dashboard/components/statistic-card/statistic-card.component';

@NgModule({
  declarations: [DashboardComponent, ChartDisplayComponent, StatisticCardComponent],
  imports: [CommonModule, SharedModule, AdminRoutingModule, NgChartsModule],
})
export class AdminModule {}
