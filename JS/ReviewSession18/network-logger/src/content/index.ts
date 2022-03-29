import { findMatchingRules } from 'core/helpers';
import { Message } from 'core/types';
import { RuleType } from 'devtools/app/rules-list/types';
import { logRequestData, logResponseData } from './logger';

chrome.runtime.onMessage.addListener(async (message: Message) => {
  const rules = await findMatchingRules(message.url, message.type);

  for (const rule of rules) {
    switch (message.type) {
      case RuleType.Request: logRequestData(message, rule); break;
      case RuleType.Response: logResponseData(message, rule); break;
    }
  }
});
