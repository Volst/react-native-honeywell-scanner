# React Native Honeywell Scanner

This package works with Honeywell devices that have an integrated barcode scanner, like the Honeywell Dolphin CT50. This package was fully tested with a CT50, since the SDK is not specific to the CT50 other devices will likely work as well but this is not guaranteed.

**TODO**:

- Add a function to determine whether device is a Honeywell Dolphin device. See https://stackoverflow.com/questions/31585811/android-honeywell-dolphin-how-to-detect-laser-scanner0
- Check if activity and lifecycle classes are necessary
- Check if you need to manually add permissions
- Publish on GitHub

## Installation

```
yarn add react-native-honeywell-scanner
```

To install the native dependencies:

```
react-native link react-native-honeywell-scanner
```

## Usage

The barcode reader needs to be "claimed" by your application; meanwhile no other application can use it. You can do that like this:

```js
import HoneywellScanner from 'react-native-honeywell-scanner';

HoneywellScanner.startReader().then((claimed) => {
    console.log(claimed ? 'Barcode reader is claimed' : 'Barcode reader is busy');
});
```

To free the claim and stop the reader, also freeing up resources:

```js
HoneywellScanner.stopReader().then(() => {
    console.log('Freedom!');
});
```

To get events from the barcode scanner:

```js
HoneywellScanner.on('barcodeReadSuccess', data => {
    console.log('Received data', data);
});

HoneywellScanner.on('barcodeReadFail', () => {
    console.log('Barcode read failed');
});
```

To stop receiving events:

```js
function barcodeReadFail = () => console.log('Barcode read failed');
HoneywellScanner.off('barcodeReadFail', handler);
```


## Inspiration

The [react-native-bluetooth-serial](https://github.com/rusel1989/react-native-bluetooth-serial) project was used as inspiration. [cordova-honeywell](https://github.com/icsfl/cordova-honeywell) also served as some inspiration.