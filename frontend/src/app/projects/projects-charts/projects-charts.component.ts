import {AfterViewInit, ChangeDetectionStrategy, Component, ElementRef, Input, ViewChild} from '@angular/core';
import {ChartData} from '../../shared/domain/ChartData';

@Component({
  selector: 'app-projects-charts',
  templateUrl: './projects-charts.component.html',
  styleUrls: ['./projects-charts.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ProjectsChartsComponent implements AfterViewInit {
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

  @ViewChild('Container') container: ElementRef;

  public view: number[] = [800, 400];
  public colorScheme = {
    domain: ['#66CCFF', '#0099CC', '#CCFF00', '#00CC66']
  };

  public getPartialChartData(length: number): ChartData[] {
    return this.chartData.slice(0, length);
  }

  public onResize(size) {
    this.view = [size * 0.9, 400];
  }

  ngAfterViewInit(): void {
    this.onResize(this.container.nativeElement.offsetWidth );
  }
}
