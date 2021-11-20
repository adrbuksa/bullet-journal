import { EntryStatus } from '../enum/entry-status';
import { EntryType } from '../enum/entry-type';

export interface Entry {
  id: number;
  text: string;
  type: EntryType;
  date: Date;
  status: EntryStatus;
  isImportant: boolean;
}
