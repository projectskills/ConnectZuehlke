import {Component, Input, OnInit} from '@angular/core';
import {ProjectService} from '../../core/services/project.service';
import {Observable} from 'rxjs';
import {EmployeeRating} from '../../shared/domain/EmployeeRating';
import {tap} from 'rxjs/operators';


@Component({
  selector: 'app-project-fitting-employees-list',
  templateUrl: './project-fitting-employees-table.component.html',
  styleUrls: ['./project-fitting-employees-table.component.scss']
})
export class ProjectFittingEmployeesTableComponent implements OnInit {
  fittingEmployeesLoading = true;
  displayedColumns: string[] = ['firstName', 'lastName', 'rating'];

  dataSource: Observable<EmployeeRating[]>;

  constructor(private projectService: ProjectService) {
  }

  @Input() projectId: string;

  ngOnInit() {
    this.dataSource = this.projectService.getProjectFittingEmployees(this.projectId).pipe(
      tap(() => this.fittingEmployeesLoading = false)
    );
  }

}
