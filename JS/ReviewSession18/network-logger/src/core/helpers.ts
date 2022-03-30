import { RuleType } from 'devtools/app/rules-list/types';
import { ConfigStorage } from './config-storage';
import { Message, Request, Response } from './types';

export function sendResponseData(data: Omit<Response, 'type'>) {
  sendToActiveTab({ type: RuleType.Response, ...data });
}

export function sendRequestData(data: Omit<Request, 'type'>) {
  sendToActiveTab({ type: RuleType.Request, ...data });
}

function sendToActiveTab(data: Message) {
  chrome.tabs.query({ active: true }, tabs => {
    const activeTab = tabs[0].id;
    if (activeTab != undefined) {
      chrome.tabs.sendMessage(activeTab, data);
    }
  });
}

export function getJsonValue<T extends Record<string, any>>(path: string, obj: T) {
  const parts = path.replace(/\[/g, '.').replace(/\]/g, '').split('.').filter(x => x);
  return parts.reduce((o, f) => (o && o[f] ? o[f] : null), obj);
}

export function isValidJson(data: string) {
  try {
    const o = JSON.parse(data);
    if (o && typeof o === 'object')
      return true;
  } catch (e) {}
  return false;
}

export async function findMatchingRules(url: string, type: RuleType) {
  const rules = await ConfigStorage.getRules();
  return rules.filter(rule => rule.type === type && url.includes(rule.url));
}
