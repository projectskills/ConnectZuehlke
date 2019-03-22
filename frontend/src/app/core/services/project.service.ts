import {Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';
import {Project} from '../../shared/domain/Project';
import {HttpClient} from '@angular/common/http';
import {catchError} from 'rxjs/operators';
import {SkillRating} from '../../shared/domain/SkillRating';
import {Employee} from "../../shared/domain/Employee";
import {EmployeeRating} from "../../shared/domain/EmployeeRating";

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  constructor(private http: HttpClient) {
  }

  public getAllProjects(): Observable<Project[]> {
    return this.http.get<Project[]>('/api/projects')
      .pipe(catchError(this.handleError('getAllProjects', [])));
  }

  public getProject(projectId: string): Observable<Project> {
    return this.http.get<Project>(`/api/projects/${projectId}`)
      .pipe(catchError(this.handleError('getProject', null)));
  }

  public getProjectSkillRatings(projectId: string): Observable<SkillRating[]> {
    return this.http.get<SkillRating[]>(`/api/projects/${projectId}/skills`)
      .pipe(catchError(this.handleError('getProjectSkillRatings', [])));
  }

  public getProjectFittingEmployees(projectId: string): Observable<EmployeeRating[]> {
    return this.http.get<EmployeeRating[]>(`api/projects/${projectId}/fittingemployees`)
      .pipe(catchError(this.handleError('getProjectFittingEmployees', [])))
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  private log(s: string) {
    console.log(`${this}: ${s}`);
  }
}
