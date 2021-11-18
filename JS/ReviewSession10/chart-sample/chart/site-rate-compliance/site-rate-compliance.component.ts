import {Component, Input} from '@angular/core';


@Component({
  selector: 'app-site-rate-compliance',
  templateUrl: './site-rate-compliance.component.html',
  styleUrls: ['./site-rate-compliance.component.scss']
})
export class SiteRateComplianceComponent {
  @Input() title = 'audit_site_trend';
  @Input() chartUrl = 'getWardTrendChart';
  @Input() chartType = 'auditSite';
  @Input() filterOptions: string[] = ['locations', 'professions'];

}
