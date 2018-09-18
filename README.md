# React Native Honeywell Scanner

This package works with Honeywell devices that have an integrated barcode scanner, like the Honeywell Dolphin CT50. This package was fully tested with a CT50, since the SDK is not specific to the CT50 other devices will likely work as well but this is not guaranteed.

**Tip**: Use [react-native-camera](https://github.com/react-native-community/react-native-camera) as fallback for devices that don't have an integrated scanner; it has an integrated barcode scanner by using the camera.

## Installation

```
yarn add react-native-honeywell-scanner
```

To install the native dependencies:

```
react-native link react-native-honeywell-scanner
```

## Usage

First you'll want to check whether the device is a Honeywell scanner:

```js
import HoneywellScanner from 'react-native-honeywell-scanner';

HoneywellScanner.isCompatible // true or false
```

The barcode reader needs to be "claimed" by your application; meanwhile no other application can use it. You can do that like this:

```js
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
HoneywellScanner.on('barcodeReadSuccess', event => {
    console.log('Received data', event);
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
