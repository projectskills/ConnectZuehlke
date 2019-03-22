import {Component, OnInit} from '@angular/core';
import {ProjectService} from '../../core/services/project.service';
import {Observable} from 'rxjs';
import {Project} from '../../shared/domain/Project';
import {Router} from '@angular/router';
import {tap} from 'rxjs/operators';

@Component({
  selector: 'app-projects-list',
  templateUrl: './projects-list.component.html',
  styleUrls: ['./projects-list.component.scss']
})
export class ProjectsListComponent implements OnInit {
  public projects: Observable<Project[]>;
  public searchQuery: string;
  public isLoading = true;

  constructor(private projectService: ProjectService,
              private router: Router) {
  }

  ngOnInit() {
    this.projects = this.projectService.getAllProjects().pipe(
      tap(() => this.isLoading = false)
    );
  }

  cardClicked(projectId: string) {
    this.router.navigate([`/projects/${projectId}`]);
  }
}
