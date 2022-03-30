export const enum RuleType {
  Request,
  Response
}

export interface Rule {
  id: string;
  type: RuleType;
  path: string;
  url: string;
}
