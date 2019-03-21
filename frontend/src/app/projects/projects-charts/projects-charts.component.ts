import {Component, Input} from '@angular/core';
import {ChartData} from '../../shared/domain/ChartData';

@Component({
  selector: 'app-projects-charts',
  templateUrl: './projects-charts.component.html',
  styleUrls: ['./projects-charts.component.scss']
})
export class ProjectsChartsComponent {
  _chartData: ChartData[];
  get chartData(): ChartData[] {
    return this._chartData;
  }

  @Input('chartData')
  set chartData(value: ChartData[]) {
    this._chartData = value
      .sort((a, b) => b.value - a.value)
      .map(chartData => ({value: chartData.value * 100, name: chartData.name}) as ChartData);
  }

  public colorScheme = {
    domain: ['#66CCFF', '#0099CC', '#CCFF00', '#00CC66']
  };

  public view = [700, 200];

  public getPartialChartData(length: number): ChartData[] {
    return this.chartData.slice(0, length);
  }

}
