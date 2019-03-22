import {Component, Input, OnInit} from '@angular/core';
import {ProjectService} from "../../core/services/project.service";
import {Observable, of} from "rxjs";
import {EmployeeRating} from "../../shared/domain/EmployeeRating";

const ELEMENT_DATA = [
  {rating: 1.0},
  {rating: 2.0},
  {rating: 3},
  {rating: 4},
];

@Component({
  selector: 'app-project-fitting-employees-list',
  templateUrl: './project-fitting-employees-list.component.html',
  styleUrls: ['./project-fitting-employees-list.component.scss']
})
export class ProjectFittingEmployeesListComponent implements OnInit {
  displayedColumns: string[] = ['firstName', 'lastName','rating'];
  dataSource: Observable<EmployeeRating[]>;

  constructor(private projectService: ProjectService) {
  }

  @Input() projectId: string;

  ngOnInit() {
    this.dataSource = this.projectService.getProjectFittingEmployees(this.projectId);
  }

}
