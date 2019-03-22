import {ChangeDetectionStrategy, Component, Input} from '@angular/core';
import {ChartData} from '../../shared/domain/ChartData';

@Component({
  selector: 'app-projects-charts',
  templateUrl: './projects-charts.component.html',
  styleUrls: ['./projects-charts.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ProjectsChartsComponent {
  private pchartData: ChartData[];
  get chartData(): ChartData[] {
    return this.pchartData;
  }

  @Input('chartData')
  set chartData(chartData: ChartData[]) {
    this.pchartData = (chartData || [])
      .sort((a, b) => b.value - a.value)
      .map(data => ({value: data.value * 100, name: data.name}) as ChartData);
  }

  public colorScheme = {
    domain: ['#66CCFF', '#0099CC', '#CCFF00', '#00CC66']
  };

  public getPartialChartData(length: number): ChartData[] {
    return this.chartData.slice(0, length);
  }
}
