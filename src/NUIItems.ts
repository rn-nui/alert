import type { ItemButton, ItemOptions } from './Alert';
import NativeAlert from './NativeAlert';

type NUIItemOptions = ItemOptions & {
  title: string | undefined;
  items: Array<string>;
};

export class NUIItems {
  private options: NUIItemOptions;

  constructor(
    title: string | undefined,
    private items?: Array<ItemButton>,
    options: ItemOptions = {}
  ) {
    this.options = {
      title: title,
      items:
        this.items
          ?.filter((i) => Boolean(i.text))
          .map((i) => i.text as string) || [],
      ...(options || {}),
    };
  }

  show() {
    NativeAlert.items(
      this.options,
      this.callback.bind(this),
      this.error.bind(this)
    );
  }

  private callback(pressed: string | number) {
    if (pressed === 'dismissed') {
      this.options.onDismiss?.();
    } else {
      findButtonByIndex(
        this.items!,
        parseInt(pressed.toString(), 10)
      )?.onPress?.();
    }
  }

  private error() {}
}

function findButtonByIndex(
  buttons: Array<ItemButton>,
  index: number
): ItemButton | undefined {
  if (!buttons || index < 0 || index >= buttons.length) return undefined;

  return buttons[index];
}
