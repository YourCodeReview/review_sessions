import {Component, Input} from '@angular/core';


@Component({
  selector: 'app-site-group-rate-compliance',
  templateUrl: './site-group-rate-compliance.component.html',
  styleUrls: ['./site-group-rate-compliance.component.scss']
})
export class SiteGroupRateComplianceComponent {
  @Input() title = 'audit_site_group_trend';
  @Input() chartUrl = 'getWardGroupTrendChart';
  @Input() chartType = 'auditSiteGroup';
  @Input() filterOptions: string[] = ['locationGroups'];
}
