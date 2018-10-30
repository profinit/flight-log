import {BackendService} from '../services/backend-service';
import {inject} from 'aurelia-framework';

@inject(BackendService)
export class Report {

  constructor(backendService) {
    this.backendService = backendService;

  }

  activate(params) {
    this.reportUrl = this.backendService.getFlightReportUrl();
  }
}
