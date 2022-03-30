import { getJsonValue, isValidJson } from 'core/helpers';
import { Request, Response } from 'core/types';
import { Rule, RuleType } from 'devtools/app/rules-list/types';

type LogData = Record<string, unknown> | string;

const makeLogger = (url: string, type: RuleType) => (data: LogData) => {
  const header = type === RuleType.Request ? 'request' : 'response';
  const headerColor = type === RuleType.Request ? '#0372AA' : '#F7A454';
  console.group(`%c[netlogger ${header}]`, `color: ${headerColor}`);
  console.log(`url: %c${url}`, 'font-style: italic');
  console.log(data);
  console.groupEnd();
};

export function logRequestData(request: Request, rule: Rule) {
  const { postData, query: queryString } = request;
  const { path, url } = rule;
  const log = makeLogger(url, request.type);
  if (path === '*') {
    log({ postData, queryString });
  } else if (path === '?') {
    log({ queryString });
  } else if (path) {
    const value = postData ? getJsonValue(path, postData) : '';
    log(value);
  }
}

export function logResponseData(response: Response, rule: Rule) {
  if (isValidJson(response.data)) {
    const parsed = JSON.parse(response.data);
    const { path, url } = rule;
    const log = makeLogger(url, response.type);
    if (path === '*') {
      log(parsed);
    } else if (path) {
      const value = getJsonValue(path, parsed);
      log(value);
    }
  }
}
