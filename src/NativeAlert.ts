import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';

export interface Spec extends TurboModule {
  alert(
    options: Object,
    callback: (pressed: string) => void,
    errorCallback: () => void
  ): void;

  prompt(
    options: Object,
    callback: (pressed: string, values: string[]) => void,
    errorCallback: () => void
  ): void;

  items(
    options: Object,
    callback: (pressed: string, values: string[]) => void,
    errorCallback: () => void
  ): void;

  singleChoice(
    options: Object,
    callback: (pressed: string, selectedValue: string) => void,
    errorCallback: () => void
  ): void;

  multiChoice(
    options: Object,
    callback: (pressed: string, state: boolean[]) => void,
    errorCallback: () => void
  ): void;
}

export default TurboModuleRegistry.getEnforcing<Spec>('Alert');
