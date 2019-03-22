import {Observable, of} from 'rxjs';
import {Project} from '../shared/domain/Project';
import {SkillRating} from '../shared/domain/SkillRating';

export const PROJECTS: Project[] = [
  {
    code: 'c1234',
    name: 'Project 1',
    description: 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt' +
      ' ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et' +
      ' ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum' +
      ' dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore' +
      ' magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet' +
      ' clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.',
    teamSize: 3
  },
  {
    code: 'c4554',
    name: 'Test 2',
    description: 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt' +
      ' ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et' +
      ' ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum' +
      ' dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore' +
      ' magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet' +
      ' clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.',
    teamSize: 2
  },
  {
    code: 'c23134',
    name: 'Amazing 3',
    description: 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt' +
      ' ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et' +
      ' ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum' +
      ' dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore' +
      ' magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet' +
      ' clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.',
    teamSize: 10
  },
  {
    code: 'q1234',
    name: 'Beer 4',
    description: 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt' +
      ' ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et' +
      ' ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum' +
      ' dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore' +
      ' magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet' +
      ' clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.',
    teamSize: 6
  },
  {
    code: 'q123456',
    name: 'Pizza 5',
    description: 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt' +
      ' ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et' +
      ' ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum' +
      ' dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore' +
      ' magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet' +
      ' clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.',
    teamSize: 8
  }
];

export class ProjectServiceMock {
  public getAllProjects(): Observable<Project[]> {
    return of(PROJECTS);
  }

  public getProject(code: string): Observable<Project> {
    return of(PROJECTS.find(e => e.code === code));
  }

  public getProjectSkillRatings(projectId: string): Observable<SkillRating[]> {
    return of<SkillRating[]>([
      {
        skill: {
          name: 'Java',
          id: 'id123'
        },
        rating: 0.6
      },
      {
        skill: {
          name: 'Springboot',
          id: 'id153'
        },
        rating: 0.2
      },
      {
        skill: {
          name: 'Angular',
          id: 'id163'
        },
        rating: 0.2
      }
    ]);
  }
}
