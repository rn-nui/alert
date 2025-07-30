import type { AlertButton, AlertOptions } from '.';
import NativeAlert from './NativeAlert';

type NUIAlertOptions = AlertOptions & {
  title: string;
  message?: string;
  items: Array<AlertButton>;
};

export class NUIAlert {
  private options: NUIAlertOptions;

  DEFAULT_BUTTON: AlertButton = {
    text: 'OK',
    position: 'positive',
  };

  constructor(
    title: string,
    message?: string,
    private items?: Array<AlertButton>,
    options: AlertOptions = {}
  ) {
    this.options = {
      title: title,
      message: message,
      items: this.items || [this.DEFAULT_BUTTON],
      ...(options || {}),
    };
  }

  show() {
    NativeAlert.alert(
      this.options,
      this.callback.bind(this),
      this.error.bind(this)
    );
  }

  callback(pressed: string) {
    if (pressed === 'dismissed') {
      this.options.onDismiss?.();
    } else if (pressed === 'positive') {
      findButtonByPosition(this.options.items, 'positive')?.onPress?.();
    } else if (pressed === 'negative') {
      findButtonByPosition(this.options.items, 'negative')?.onPress?.();
    } else if (pressed === 'neutral') {
      findButtonByPosition(this.options.items, 'neutral')?.onPress?.();
    }
  }

  error() {}
}

function findButtonByPosition(
  buttons: Array<AlertButton>,
  position: 'neutral' | 'negative' | 'positive'
): AlertButton | undefined {
  return buttons.find((item) => item.position === position);
}
