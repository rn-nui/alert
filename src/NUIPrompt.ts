import type {
  PromptButton,
  PromptOptions,
  PromptType,
  ReturnValue,
} from './Alert';
import NativeAlert from './NativeAlert';

type NUIPromptOptions = PromptOptions & {
  title: string;
  message?: string;
  items: Array<PromptButton>;
  type: PromptType;
  defaultValue: string | undefined;
  keyboardType: string | undefined;
};

export class NUIPrompt {
  private options: NUIPromptOptions;

  DEFAULT_BUTTON: PromptButton = {
    text: 'OK',
    position: 'positive',
  };

  constructor(
    title: string,
    message: string | undefined,
    private items: Array<PromptButton> | undefined,
    type: PromptType = 'plain-text',
    defaultValue: string | undefined,
    keyboardType: string | undefined,
    options: PromptOptions = {}
  ) {
    this.options = {
      title: title,
      message: message,
      items: this.items || [this.DEFAULT_BUTTON],
      type,
      defaultValue,
      keyboardType,
      ...(options || {}),
    };
  }

  show() {
    NativeAlert.prompt(
      this.options,
      this.callback.bind(this),
      this.error.bind(this)
    );
  }

  private callback(pressed: string, values: string[]) {
    if (pressed === 'dismissed') {
      this.options.onDismiss?.(this.parseValues(values));
    } else if (pressed === 'positive') {
      findButtonByPosition(this.options.items, 'positive')?.onPress?.(
        this.parseValues(values)
      );
    } else if (pressed === 'negative') {
      findButtonByPosition(this.options.items, 'negative')?.onPress?.(
        this.parseValues(values)
      );
    } else if (pressed === 'neutral') {
      findButtonByPosition(this.options.items, 'neutral')?.onPress?.(
        this.parseValues(values)
      );
    }
  }

  private error() {}

  private parseValues(values: string[]): ReturnValue {
    if (this.options.type === 'login-password') {
      const [login, password] = values;
      return { login: login || '', password: password || '' };
    }

    return values[0] || '';
  }
}

function findButtonByPosition(
  buttons: Array<PromptButton>,
  position: 'neutral' | 'negative' | 'positive'
): PromptButton | undefined {
  return buttons.find((item) => item.position === position);
}
