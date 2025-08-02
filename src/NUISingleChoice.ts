import type { SingleChoiceButton, SingleChoiceOptions } from './Alert';
import NativeAlert from './NativeAlert';

type NUISingleChoiceOptions = Omit<
  SingleChoiceOptions,
  'positive' | 'neutral' | 'negative'
> & {
  title: string;
  items: Array<string>;
  positiveText?: string | undefined;
  negativeText?: string | undefined;
  neutralText?: string | undefined;
  defaultSelectedIndex?: number | undefined;
};

export class NUISingleChoice {
  private allOptions: NUISingleChoiceOptions;

  constructor(
    title: string,
    private items?: Array<SingleChoiceButton>,
    private options: SingleChoiceOptions = {}
  ) {
    this.allOptions = {
      title: title,
      items:
        this.items?.filter((item) => !!item.text).map((item) => item.text) ||
        [],
      ...(options || {}),
      positiveText:
        options.positive?.text ||
        (missingNegativeAndNeutral(options) ? 'OK' : undefined),
      negativeText: options.negative?.text,
      neutralText: options.neutral?.text,
      defaultSelectedIndex: this.items?.findIndex(
        (item) => item.defaultSelected
      ),
    };
  }

  show() {
    NativeAlert.singleChoice(
      this.allOptions,
      this.callback.bind(this),
      this.error.bind(this)
    );
  }

  private callback(pressed: string, selectedIndex: string) {
    const selectedValue = this.items?.[parseInt(selectedIndex, 10)];

    if (pressed === 'dismissed') {
      this.options.onDismiss?.(selectedValue?.value);
    } else if (pressed === 'positive') {
      this.options.positive?.onPress?.(selectedValue?.value);
    } else if (pressed === 'negative') {
      this.options.negative?.onPress?.(selectedValue?.value);
    } else if (pressed === 'neutral') {
      this.options.neutral?.onPress?.(selectedValue?.value);
    }
  }

  private error() {}
}

function missingNegativeAndNeutral(options: SingleChoiceOptions): boolean {
  return !options.negative && !options.neutral;
}
