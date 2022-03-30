import { RuleType } from 'devtools/app/rules-list/types';

type BrowserRequest = chrome.devtools.network.Request['request'];
type PostData = Record<string, any>;
type Query = BrowserRequest['queryString'];

export interface Response {
  type: RuleType.Response;
  data: string;
  url: string;
}

export interface Request {
  type: RuleType.Request;
  postData?: PostData;
  query: Query;
  url: string;
}

export type Message = Response | Request;
