import {Component, Input} from '@angular/core';


@Component({
  selector: 'app-profession-rate-compliance',
  templateUrl: './profession-rate-compliance.component.html',
  styleUrls: ['./profession-rate-compliance.component.scss']
})
export class ProfessionRateComplianceComponent {
  @Input() title = 'profession_trend';
  @Input() chartUrl = 'getProfessionTrendChart';
  @Input() chartType = 'profession';
  @Input() filterOptions: string[] = ['professions'];
}
