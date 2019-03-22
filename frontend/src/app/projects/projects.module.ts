import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {ProjectsRoutingModule} from './projects-routing.module';
import {ProjectsListComponent} from './projects-list/projects-list.component';
import {
  MatChipsModule, MatExpansionModule,
  MatFormFieldModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatProgressSpinnerModule, MatTableModule, MatTabsModule
} from '@angular/material';
import {FormsModule} from '@angular/forms';
import {SharedModule} from '../shared/shared.module';
import {ProjectCardsComponent} from './projects-cards/project-cards.component';
import {MatCardModule} from '@angular/material/card';
import {intersectionObserverPreset, LazyLoadImageModule} from 'ng-lazyload-image';
import { ProjectsDetailComponent } from './projects-detail/projects-detail.component';
import {NgxChartsModule} from '@swimlane/ngx-charts';
import { ProjectsChartsComponent } from './projects-charts/projects-charts.component';
import { ProjectFittingEmployeesTableComponent } from './project-fitting-employees-table/project-fitting-employees-table.component';

@NgModule({
  declarations: [
    ProjectsListComponent,
    ProjectCardsComponent,
    ProjectsDetailComponent,
    ProjectsChartsComponent,
    ProjectFittingEmployeesTableComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    ProjectsRoutingModule,
    FormsModule,
    MatInputModule,
    MatFormFieldModule,
    MatListModule,
    MatCardModule,
    MatChipsModule,
    MatIconModule,
    MatProgressSpinnerModule,
    MatExpansionModule,
    MatTabsModule,
    MatTableModule,
    LazyLoadImageModule.forRoot({
      preset: intersectionObserverPreset
    }),
    NgxChartsModule
  ]
})
export class ProjectsModule {
}
