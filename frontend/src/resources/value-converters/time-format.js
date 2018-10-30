import moment from 'moment';

export class TimeFormatValueConverter {
  toView(value) {
    return moment(value).format('H:mm:ss');
  }
}
