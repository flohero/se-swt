/* tslint:disable */
/* eslint-disable */
import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';
import { RequestBuilder } from '../request-builder';
import { Observable } from 'rxjs';
import { map, filter } from 'rxjs/operators';

import { EmployeeDto } from '../models/employee-dto';

@Injectable({
  providedIn: 'root',
})
export class EmployeeControllerService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * Path part for operation hello
   */
  static readonly HelloPath = '/worklog/hello';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `hello()` instead.
   *
   * This method doesn't expect any request body.
   */
  hello$Response(params?: {
  }): Observable<StrictHttpResponse<string>> {

    const rb = new RequestBuilder(this.rootUrl, EmployeeControllerService.HelloPath, 'get');
    if (params) {
    }

    return this.http.request(rb.build({
      responseType: 'text',
      accept: 'text/plain'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<string>;
      })
    );
  }

  /**
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `hello$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  hello(params?: {
  }): Observable<string> {

    return this.hello$Response(params).pipe(
      map((r: StrictHttpResponse<string>) => r.body as string)
    );
  }

  /**
   * Path part for operation getEmployees
   */
  static readonly GetEmployeesPath = '/worklog/employees';

  /**
   * get all employees.
   *
   * list of employee data
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getEmployees()` instead.
   *
   * This method doesn't expect any request body.
   */
  getEmployees$Response(params?: {
  }): Observable<StrictHttpResponse<Array<EmployeeDto>>> {

    const rb = new RequestBuilder(this.rootUrl, EmployeeControllerService.GetEmployeesPath, 'get');
    if (params) {
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<Array<EmployeeDto>>;
      })
    );
  }

  /**
   * get all employees.
   *
   * list of employee data
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getEmployees$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getEmployees(params?: {
  }): Observable<Array<EmployeeDto>> {

    return this.getEmployees$Response(params).pipe(
      map((r: StrictHttpResponse<Array<EmployeeDto>>) => r.body as Array<EmployeeDto>)
    );
  }

}
