import type { MultiChoiceButton, MultiChoiceOptions } from './Alert';
import NativeAlert from './NativeAlert';

type NUIMultiChoiceOptions = Omit<
  MultiChoiceOptions,
  'positive' | 'neutral' | 'negative'
> & {
  title: string;
  items: Array<string>;
  positiveText?: string | undefined;
  negativeText?: string | undefined;
  neutralText?: string | undefined;
  defaultSelections: boolean[];
};

export class NUIMultiChoice {
  private allOptions: NUIMultiChoiceOptions;

  constructor(
    title: string,
    private items?: Array<MultiChoiceButton>,
    private options: MultiChoiceOptions = {}
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
      defaultSelections:
        this.items?.map((item) => item.defaultSelected ?? false) ||
        Array(this.items?.length).fill(false),
    };
  }

  show() {
    NativeAlert.multiChoice(
      this.allOptions,
      this.callback.bind(this),
      this.error.bind(this)
    );
  }

  private callback(pressed: string, state: boolean[]) {
    const selectedValues =
      this.items?.filter((_, index) => state[index]).map((i) => i.value) || [];

    if (pressed === 'dismissed') {
      this.options.onDismiss?.(selectedValues);
    } else if (pressed === 'positive') {
      this.options.positive?.onPress?.(selectedValues);
    } else if (pressed === 'negative') {
      this.options.negative?.onPress?.(selectedValues);
    } else if (pressed === 'neutral') {
      this.options.neutral?.onPress?.(selectedValues);
    }
  }

  private error() {}
}

function missingNegativeAndNeutral(options: MultiChoiceOptions): boolean {
  return !options.negative && !options.neutral;
}
