import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ProjectsListComponent} from './projects-list/projects-list.component';
import {ProjectsDetailComponent} from './projects-detail/projects-detail.component';

const routes: Routes = [
  {
    path: '',
    component: ProjectsListComponent
  },
  {
    path: ':projectId',
    component: ProjectsDetailComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProjectsRoutingModule {
}
