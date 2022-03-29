import { uuid } from 'common/uuid';
import { Rule, RuleType } from 'devtools/app/rules-list/types';

export const getDefaultRules = (): Rule[] => [
  {
    id: uuid(),
    type: RuleType.Response,
    url: '/get/something',
    path: 'items[0].name',
  },
  {
    id: uuid(),
    type: RuleType.Request,
    url: '/put/something-else',
    path: 'items[0].value',
  },
];
