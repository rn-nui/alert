import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';

export interface Spec extends TurboModule {
  alert(
    options: Object,
    callback: (pressed: string) => void,
    errorCallback: () => void
  ): void;
}

export default TurboModuleRegistry.getEnforcing<Spec>('Alert');
