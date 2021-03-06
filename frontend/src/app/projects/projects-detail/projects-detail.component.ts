import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Observable, Subscription} from 'rxjs';
import {Project} from '../../shared/domain/Project';
import {ProjectService} from '../../core/services/project.service';
import {map, tap} from 'rxjs/operators';
import {ChartData} from '../../shared/domain/ChartData';

@Component({
  selector: 'app-projects-detail',
  templateUrl: './projects-detail.component.html',
  styleUrls: ['./projects-detail.component.scss']
})
export class ProjectsDetailComponent implements OnInit, OnDestroy {
  private subscription: Subscription;
  projectId: string;
  project$: Observable<Project>;
  projectLoading = true;
  chartLoading = true;

  chartData$: Observable<ChartData[]>;

  constructor(private route: ActivatedRoute,
              private projectService: ProjectService) {
  }

  ngOnInit() {
    this.subscription = this.route.params.subscribe(params => {
      this.projectId = params.projectId;
      this.project$ = this.projectService.getProject(this.projectId)
        .pipe(tap(() => this.projectLoading = false));
      this.chartData$ = this.projectService.getProjectSkillRatings(this.projectId).pipe(
        tap(() => this.chartLoading = false),
        map(skillRating => {
          return skillRating.map(result => ({name: result.skill.name, value: result.rating} as ChartData));
        })
      );
    });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
