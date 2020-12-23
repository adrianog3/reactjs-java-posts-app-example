import moment from "moment";

export function formatDate(date: string): string {
  const newMoment = moment(new Date(date));
  const formattedDate = newMoment.format("DD/MM/YYYY");
  const formattedHours = newMoment.format("HH:mm");

  return `${formattedDate} às ${formattedHours}`;
}
