import {Component, OnInit} from '@angular/core';
import {EmployeeControllerService} from '../api/services';
import {EmployeeDto} from '../api/models';
import {HttpErrorResponse} from '@angular/common/http';

@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.css']
})
export class EmployeeListComponent implements OnInit {

  employeeList: EmployeeDto[] = [];
  errorInfo: string | null = null;

  constructor(private employeeService: EmployeeControllerService) {
  }

  ngOnInit(): void {
    this.employeeService.getEmployees()
      .subscribe({
        next: (employees: EmployeeDto[]) => {
          this.employeeList = employees;
          this.resetError();
        },
        error: (error: HttpErrorResponse) => {
          this.displayError(error);
        }
      });
  }

  private displayError(resp: HttpErrorResponse): void {
    this.errorInfo = `${resp.error.error} (${resp.error.status}): ${resp.error.message}.`;
  }

  private resetError(): void {
    this.errorInfo = null;
  }
}
