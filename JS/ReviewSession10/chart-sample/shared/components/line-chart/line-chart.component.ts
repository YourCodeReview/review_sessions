import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Chart} from 'angular-highcharts';
import {CommonService} from '../../../../shared/services/api/common.service';
import * as moment from 'moment';
import {SettingsService} from '../../../../shared/services/api/settings.service';
import {WardsService} from '../../../../shared/services/api/wards.service';
import {ProfessionsService} from '../../../../shared/services/api/professions.service';
import {ChartService} from '../../../../shared/services/api/chart.service';
import {CrossAuditService} from '../../../../shared/services/api/cross-audit.service';
import {EMPTY, forkJoin, Observable, Subject} from 'rxjs';
import {SessionService} from '../../../../shared/services/session.service';
import {catchError, debounceTime, distinctUntilChanged, switchMap, takeUntil} from 'rxjs/operators';
import {IUserSession} from '../../../../shared/models/session.model';
import {TranslateService} from '@ngx-translate/core';
import Utils from '../../../../shared/utils/utils';
import {DropdownItemsService} from '../../services/dropdown-items.service';
import {FormBuilder, FormGroup} from '@angular/forms';

@Component({
  selector: 'app-line-chart',
  templateUrl: './line-chart.component.html',
  styleUrls: ['./line-chart.component.scss']
})
export class LineChartComponent implements OnInit, OnDestroy {
  trendChart: Chart;
  filterForm: FormGroup;
  @Input() title = '';
  @Input() chartUrl = '';
  @Input() chartType = 'auditSite';
  @Input() filterOptions: string[] = [];
  years: any[] = [];
  months: any[];
  targetCompanies = [];
  locations = [];
  professions = [];
  locationGroups = [];
  professionGroups = [];
  benchData: any;
  loader = false;
  selectedHospital;
  privileges;
  optionLoader = true;
  chartLoader = true;
  userSession: IUserSession;
  companyFromStorage;
  private unsubscribe$ = new Subject();

  constructor(
    private commonService: CommonService,
    private settingsService: SettingsService,
    private sessionService: SessionService,
    private chartService: ChartService,
    private translate: TranslateService,
    private crossService: CrossAuditService,
    private dropdownItemsService: DropdownItemsService,
    private fb: FormBuilder
  ) {
    this.filterForm = this.fb.group({
      reportCriteria: ['Monthly'],
      startMonth: [''],
      endMonth: [''],
      years: [[]],
      professions: [[]],
      locations: [[]],
      locationGroups: [[]],
      professionGroups: [[]]
    });
  }

  ngOnInit() {

    this.sessionService.userSession.pipe(takeUntil(this.unsubscribe$)).subscribe(session => {
      this.userSession = session;
    });

    this.loadOptions();
    this.initialLoad().then(() => {
      // subscribe to filter form and load chart
      this.filterForm.valueChanges
        .pipe(
          debounceTime(500),
          distinctUntilChanged(),
          switchMap(
            formVal => this.getChartDataObs(formVal)
              .pipe(catchError((error) => {
                this.chartLoader = false;
                return EMPTY;
              }))
          )
        )
        .subscribe(value => {
          this.chartLoader = false;
          this.buildChart(value);
        });
      this.filterForm.patchValue({years: [moment().year()]});
      this.filterForm.patchValue({startMonth: {name: moment.months()[0], value: 1}});
      this.filterForm.patchValue({endMonth: {name: moment.months()[11], value: 12}});
    }, error => {
    });
  }

  getChartDataObs(filters): Observable<any> {
    this.chartLoader = true;
    const chartBody = {
      years: filters.years ? filters.years.map(year => year.toString()) : [],
      startMonth: filters.reportCriteria === 'Quarterly' ? this.userSession.startMonth : filters.startMonth.value,
      endMonth: filters.reportCriteria === 'Quarterly' ? this.userSession.endMonth : filters.endMonth.value,
      reportCriteria: filters.reportCriteria
    };
    Object.assign(
      chartBody,
      filters.locations.length ? {wards: filters.locations.map(location => location.value)} : null,
      !!filters.professions.length ? {professions: filters.professions.map(profession => profession.value)} : null,
      !!filters.locationGroups.length ? {wardGroups: filters.locationGroups.map(locationGroup => locationGroup.value)} : null,
      !!filters.professionGroups.length ? {professionGroups: filters.professionGroups.map(professionGroup => professionGroup.value)} : null
    );
    return this.chartService[this.chartUrl](chartBody, this.companyFromStorage ? this.companyFromStorage.id : this.selectedHospital);
  }


  get formVal() {
    console.log('fff');
    return this.filterForm.getRawValue();
  }

  loadOptions() {

    //clear dropdown selected items
    this.filterForm.patchValue({locations: []});
    this.filterForm.patchValue({professions: []});
    this.filterForm.patchValue({locationGroups: []});
    this.filterForm.patchValue({professionGroups: []});

    if (this.filterOptions.includes('locations')) {
      this.dropdownItemsService.loadLocationList(this.companyFromStorage ? this.companyFromStorage.id : this.selectedHospital).subscribe(data => {
        this.locations = data.map(location => {
          return {name: location.name, value: location.id};
        });
      });
    }

    if (this.filterOptions.includes('professions')) {
      this.dropdownItemsService.loadProfessionList(this.companyFromStorage ? this.companyFromStorage.id : this.selectedHospital).subscribe(data => {
        this.professions = data.map(profession => {
          return {name: profession.name, value: profession.id};
        });
      });
    }

    if (this.filterOptions.includes('locationGroups')) {
      this.dropdownItemsService.loadLocationGroupList(this.companyFromStorage ? this.companyFromStorage.id : this.selectedHospital).subscribe(data => {
        this.locationGroups = data.map(locationGroup => ({name: locationGroup.name, value: locationGroup.id}));
      });
    }

    if (this.filterOptions.includes('professionGroups')) {
      this.dropdownItemsService.loadProfessionGroupList(this.companyFromStorage ? this.companyFromStorage.id : this.selectedHospital).subscribe(data => {
        this.professionGroups = data.map(professionGroup => ({name: professionGroup.name, value: professionGroup.id}));
      });
    }

  }

  async initialLoad() {
    //load user privileges
    this.sessionService.userPrivileges.pipe(takeUntil(this.unsubscribe$)).subscribe(response => {
      this.privileges = response;
    });

    if (this.privileges['Cluster Charts']) {
      await this.loadCrossHospitals();
    }
    await this.getDatesOpt();
    await this.getBenchmarkingData();
    this.optionLoader = false;
  }

  loadCrossHospitals() {
    return new Promise<void>(resolve => {
      this.crossService.getTargetHospitalDropdown().subscribe(response => {
        this.targetCompanies = response.map(company => ({label: company.name, value: company.id}));
        resolve();
      });
    });
  }

  getDatesOpt() {
    return new Promise<void>(resolve => {
      this.translate.stream('success').subscribe(() => {
        Utils.setLocale();
        this.months = moment.months().map((month, index) => (
          {name: month, value: index + 1}
        ));
      });
      this.commonService.getDataYears(this.companyFromStorage ? this.companyFromStorage.id : this.selectedHospital).subscribe(yearsArr => {
        this.years = yearsArr;
        this.years.push({label: moment().year() + 1, value: moment().year() + 1});
        resolve();
      });
    });
  }

  getBenchmarkingData() {
    return new Promise<void>(resolve => {
      this.settingsService.getBenchData().subscribe(response => {
        this.benchData = response;
        resolve();
      });
    });
  }


  buildChart(chartData?) {
    console.log('chart');
    this.trendChart = new Chart({
      chart: {
        type: 'line'
      },
      lang: Utils.chartExportTranslations ? Utils.chartExportTranslations : {},
      title: {
        text: `${this.companyFromStorage ? this.companyFromStorage.name : this.selectedHospital ? this.targetCompanies.find(company => company.value === this.selectedHospital).label : ''} ${this.translate.instant(this.title)}`
      },
      subtitle: {
        text: `${this.formVal.years ? this.formVal.years.join(', ') : ''}${this.formVal.locations.length ? '<br>' : ''}${this.formVal.locations.map(location => location.name).join(', ')}${this.formVal.professions.length ? '<br>' : ''}${this.formVal.professions.map(profession => profession.name).join(', ')}${this.formVal.locationGroups.length ? '<br>' : ''}${this.formVal.locationGroups.map(locationGroup => locationGroup.name).join(', ')}${this.formVal.professionGroups.length ? '<br>' : ''}${this.formVal.professionGroups.map(professionGroup => professionGroup.name).join(', ')}`
      },
      xAxis: {
        categories: chartData ? chartData.categories : ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
      },
      yAxis: {
        max: 100,
        min: 0,
        endOnTick: false,
        tickInterval: 10,
        gridLineWidth: 0,
        minorGridLineWidth: 0,
        title: {
          text: `${this.translate.instant('compliance')} %`
        },
        plotLines: [
          {
            width: 5,
            dashStyle: 'ShortDash',
            value: this.benchData.target,
            color: 'rgba(128, 226, 108, 0.4)'
          },
          {
            width: 5,
            dashStyle: 'ShortDash',
            value: this.benchData.stretch,
            color: 'rgba(9, 133, 212, 0.4)'
          },
          {
            width: 5,
            dashStyle: 'ShortDash',
            value: this.benchData.threshold,
            color: 'rgba(191, 39, 39, 0.4)'
          }
        ]
      }
      ,
      legend: {
        shadow: false,
        maxHeight: 100
      },
      tooltip: {
        shared: true,
        valueDecimals: 1,
        pointFormat: '<span style="color:{point.color}">\u25CF</span> {series.name}: <b>{point.y}</b><br/>',
        valueSuffix: '%'
      },
      plotOptions: {
        line: {
          dataLabels: {
            format: '{point.y:.1f}%',
            enabled: true
          },
        },
        series: {
          stickyTracking: false
        }
      },
      credits: {
        enabled: false
      },
      series: chartData ? chartData.series.length ? chartData.series :
          [{name: this.translate.instant('no_data'), type: undefined}] :
        [{name: this.translate.instant('no_data'), type: undefined}],
      exporting: {
        filename: `${this.companyFromStorage ? this.companyFromStorage.name : this.selectedHospital ? this.targetCompanies.find(company => company.value === this.selectedHospital).label : ''} ${this.translate.instant(this.title)}`,
        sourceWidth: 2200,
        sourceHeight: 1000,
        chartOptions: {
          title: {
            style: {
              fontSize: '28px',
            }
          },
          legend: {
            shadow: false,
            itemStyle: {
              fontSize: '22px'
            },
            maxHeight: 100
          },
          subtitle: {
            style: {
              fontSize: '20px',
            }
          },
          xAxis: [{
            categories: chartData ? chartData.categories : [this.translate.instant('no_data')],
            labels: {
              style: {
                fontSize: '18px',
                fontWeight: 'bolder'
              }
            }
          }],
          plotOptions: {
            series: {
              dataLabels: {
                enabled: true,
                style: {
                  fontSize: '18px'
                }
              }
            }
          },

        }
      }
    });
    this.loader = false;
  }

  ngOnDestroy() {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }
}
