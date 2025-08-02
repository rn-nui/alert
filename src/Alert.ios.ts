import { Alert as RNAlert, ActionSheetIOS } from 'react-native';
import type { ItemButton, ItemOptions } from './Alert';

export default class Alert {
  /** Default alert provided by React Native */
  static alert(...props: Parameters<typeof RNAlert.alert>): void {
    RNAlert.alert(...props);
  }

  /** Default prompt provided by React Native */
  static prompt(...props: Parameters<typeof RNAlert.prompt>): void {
    RNAlert.prompt(...props);
  }

  /**
   * Show a list of items in a dialog, like a menu.
   * @param title The title of the dialog.
   * @param items An array of items to display in the dialog.
   * @param options Additional options for the item dialog.
   *
   * This allows you to display the list in an alert or as a sheet.
   * The sheet uses React Native's ActionSheetIOS.
   */
  static items(
    title?: string | undefined,
    items?: Array<ItemButton>,
    options: ItemOptions = {}
  ) {
    if (options.presentation === 'sheet') {
      showItemsSheet(title, options, items);
    } else {
      showItemsAlert(title, options, items);
    }
  }

  static singleChoice() {
    console.warn('Alert.singleChoice is only available on Android');
  }

  static multiChoice() {
    console.warn('Alert.multiChoice is only available on Android');
  }
}

function showItemsAlert(
  title: string | undefined,
  options: ItemOptions,
  items?: Array<ItemButton>
) {
  const buttons = items || [];

  if (options.cancelable) {
    buttons.push({
      text: options.cancelButtonText || 'Cancel',
      onPress: options.onDismiss,
      style: 'cancel',
    });
  }

  RNAlert.alert(title || '', options.message, buttons, {
    userInterfaceStyle: options.userInterfaceStyle,
  });
}

function showItemsSheet(
  title: string | undefined,
  options: ItemOptions,
  items?: Array<ItemButton>
) {
  const buttons = items || [];

  if (options.cancelable) {
    buttons.push({
      text: options.cancelButtonText || 'Cancel',
      onPress: options.onDismiss,
    });
  }

  const destuctiveIndex = buttons.findIndex(
    (button) => button.style === 'destructive'
  );

  const interfaceColor =
    options.userInterfaceStyle === 'unspecified'
      ? undefined
      : options.userInterfaceStyle;

  ActionSheetIOS.showActionSheetWithOptions(
    {
      cancelButtonIndex: options.cancelable ? buttons.length - 1 : -1,
      options: buttons.map((button) => button.text || ''),
      userInterfaceStyle: interfaceColor,
      title: title,
      message: options.message,
      destructiveButtonIndex:
        destuctiveIndex >= 0 ? destuctiveIndex : undefined,
    },
    (index) => {
      buttons[index]?.onPress?.();
    }
  );
}
