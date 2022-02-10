report {
    issueNamePrefix 'Issue #'
    issueUrlPrefix 'https://github.com/rabestro/algorithms/issues/'
}

spockReports {
    set(['com.athaydes.spockframework.report.showCodeBlocks'                    : true,
         'com.athaydes.spockframework.report.outputDir'                         : '../docs/spock-reports',
         'com.athaydes.spockframework.report.projectName'                       : 'Graph search algorithms',
         'com.athaydes.spockframework.report.projectVersion'                    : 1.1,
         'com.athaydes.spockframework.report.internal.HtmlReportCreator.enabled': true,
         'com.athaydes.spockframework.report.IReportCreator'                    : 'com.athaydes.spockframework.report.internal.HtmlReportCreator'
    ])
    // com.athaydes.spockframework.report.template.TemplateReportCreator
    // com.athaydes.spockframework.report.internal.HtmlReportCreator
}