import {
  type AlertOptions as RNAlertOptions,
  type AlertButton as RNAlertButton,
} from 'react-native';
import { NUIAlert } from './NUIAlert';
import { NUIPrompt } from './NUIPrompt';
import { NUIItems } from './NUIItems';
import { NUISingleChoice } from './NUISingleChoice';
import { NUIMultiChoice } from './NUIMultiChoice';

export default class Alert {
  /**
   * Show an alert dialog with a title, message, and buttons.
   * @param title The title of the alert.
   * @param message The message of the alert.
   * @param buttons An array of buttons to display in the alert.
   * @param options Additional options for the alert.
   */
  static alert(
    title: string,
    message?: string,
    buttons?: Array<AlertButton>,
    options: AlertOptions = {}
  ): void {
    new NUIAlert(title, message, buttons, options).show();
  }

  /**
   * Show a prompt dialog containing 1 or 2 input fields..
   * @param title The title of the prompt.
   * @param message The message of the prompt.
   * @param buttons An array of buttons to display in the prompt.
   * @param type The type of prompt (e.g., 'plain-text', 'secure-text', 'login-password').
   * @param defaultValue Default value for the input field (iOS only).
   * @param keyboardType Keyboard type for the input field (iOS only).
   * @param options Additional options for the prompt.
   */
  static prompt(
    title: string,
    message: string | undefined,
    buttons: Array<PromptButton> | undefined,
    type: PromptType = 'plain-text',
    /** @platform ios */
    defaultValue?: string | undefined,
    /** @platform ios */
    keyboardType?: string | undefined,
    options: PromptOptions = {}
  ) {
    new NUIPrompt(
      title,
      message,
      buttons,
      type,
      defaultValue,
      keyboardType,
      options
    ).show();
  }

  /**
   * Show a list of items in a dialog, like a menu.
   * @param title The title of the dialog.
   * @param items An array of items to display in the dialog.
   * @param options Additional options for the item dialog.
   */
  static items(
    title?: string | undefined,
    items?: Array<ItemButton>,
    options: ItemOptions = {}
  ) {
    new NUIItems(title, items, options).show();
  }

  /**
   * @platform android only
   * Show a single-choice radio group styled dialog with a list of options.
   * @param title The title of the dialog.
   * @param buttons An array of buttons representing the choices.
   * @param options Additional options for the single-choice dialog.
   */
  static singleChoice(
    title: string,
    buttons: Array<SingleChoiceButton>,
    options: SingleChoiceOptions = {}
  ) {
    new NUISingleChoice(title, buttons, options).show();
  }

  /**
   * @platform android only
   * Show a multi-choice checkbox styled dialog with a list of options.
   * @param title The title of the dialog.
   * @param items An array of buttons representing the choices.
   * @param options Additional options for the multi-choice dialog.
   */
  static multiChoice(
    title: string,
    items: Array<MultiChoiceButton>,
    options: MultiChoiceOptions = {}
  ) {
    new NUIMultiChoice(title, items, options).show();
  }
}

export type AlertOptions = RNAlertOptions & {
  /** @platform android */
  headerAlignment?: 'left' | 'center';

  /** @platform android */
  icon?: string;
};

export type ReturnValue = string | { login: string; password: string };

export type PromptOptions = AlertOptions & {
  /** @platform android */
  onDismiss?: (value: ReturnValue) => void;
};

export type AlertButton = Omit<RNAlertButton, 'onPress'> & {
  /** @platform android */
  position?: 'neutral' | 'negative' | 'positive';
  onPress?: () => void;
};

export type PromptButton = Omit<RNAlertButton, 'onPress'> & {
  /** @platform android */
  position: 'neutral' | 'negative' | 'positive';
  onPress?: (value: ReturnValue) => void;
};

export type PromptType = 'plain-text' | 'secure-text' | 'login-password';

export type ItemButton = Omit<AlertButton, 'position'>;
export type ItemOptions = Omit<AlertOptions, 'icon'> & {
  /** @platform ios */
  message?: string | undefined;

  /**
   * Defaults to false on Android and true on iOS
   */
  cancelable?: boolean | undefined;

  cancelButtonText?: string | undefined;

  /** @platform ios */
  presentation?: 'alert' | 'sheet' | undefined;
};

export type SingleChoiceButton = {
  text: string;
  value: string;
  defaultSelected?: boolean;
};

export type MultiChoiceButton = {
  text: string;
  value: string;
  defaultSelected?: boolean;
};

export type SingleChoiceOptions = Omit<AlertOptions, 'userInterfaceStyle'> & {
  positive?:
    | {
        text: string;
        onPress?: (selectedValue?: string) => void;
      }
    | undefined;
  negative?:
    | {
        text: string;
        onPress?: (selectedValue?: string) => void;
      }
    | undefined;
  neutral?:
    | {
        text: string;
        onPress?: (selectedValue?: string) => void;
      }
    | undefined;
  onDismiss?: (selectedValue?: string) => void | undefined;
};

export type MultiChoiceOptions = Omit<AlertOptions, 'userInterfaceStyle'> & {
  positive?:
    | {
        text: string;
        onPress?: (selectedValues?: string[]) => void;
      }
    | undefined;
  negative?:
    | {
        text: string;
        onPress?: (selectedValues?: string[]) => void;
      }
    | undefined;
  neutral?:
    | {
        text: string;
        onPress?: (selectedValues?: string[]) => void;
      }
    | undefined;
  onDismiss?: (selectedValues?: string[]) => void | undefined;
};
