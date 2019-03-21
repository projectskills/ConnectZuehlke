import {Component, Input} from '@angular/core';
import {ChartData} from '../../shared/domain/ChartData';

@Component({
  selector: 'app-projects-charts',
  templateUrl: './projects-charts.component.html',
  styleUrls: ['./projects-charts.component.scss']
})
export class ProjectsChartsComponent {
  @Input() chartData: ChartData[];

  public colorScheme = {
    domain: ['#5AA454', '#A10A28', '#C7B42C']
  };

  public view = [700, 200];

}
