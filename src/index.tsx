import {
  type AlertOptions as RNAlertOptions,
  type AlertButton as RNAlertButton,
} from 'react-native';
import { NUIAlert } from './NUIAlert';

export type AlertOptions = RNAlertOptions & {
  /** @platform android */
  headerAlignment?: 'left' | 'center';

  /** @platform android */
  icon?: string;
};

export type AlertButton = RNAlertButton & {
  /** @platform android */
  position?: 'neutral' | 'negative' | 'positive';
};

export default class Alert {
  static alert(
    title: string,
    message?: string,
    buttons?: Array<AlertButton>,
    options: AlertOptions = {}
  ): void {
    new NUIAlert(title, message, buttons, options).show();
  }
}
