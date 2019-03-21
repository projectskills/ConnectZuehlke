import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Observable, Subscription} from 'rxjs';
import {Project} from '../../shared/domain/Project';
import {ProjectService} from '../../core/services/project.service';
import {tap} from 'rxjs/operators';

@Component({
  selector: 'app-projects-detail',
  templateUrl: './projects-detail.component.html',
  styleUrls: ['./projects-detail.component.scss']
})
export class ProjectsDetailComponent implements OnInit, OnDestroy {
  private subscriptions: Subscription;
  project$: Observable<Project>;
  projectLoading = true;

  constructor(private route: ActivatedRoute,
              private projectService: ProjectService) { }

  ngOnInit() {
    this.subscriptions = this.route.params.subscribe(params => {
      const projectId = params.projectId;
      this.project$ = this.projectService.getProject(projectId)
        .pipe(tap(() => this.projectLoading = false));
    });
  }

  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();
  }

}
