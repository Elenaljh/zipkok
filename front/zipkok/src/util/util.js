import Swal from "sweetalert2";

const moneyFormat = (value) => {
  const numbers = [
    numbering(value % 100000000000000000000, 10000000000000000),
    numbering(value % 10000000000000000, 1000000000000),
    numbering(value % 1000000000000, 100000000),
    numbering(value % 100000000, 10000),
    value % 10000,
  ];

  return setUnitText(numbers)
    .filter((number) => !!number)
    .join(" ");
};

const adjustedMoneyFormat = (value) => {
  const numbers = [
    numbering(value % 10000000000000000, 1000000000000),
    numbering(value % 1000000000000, 100000000),
    numbering(value % 100000000, 10000),
    value % 10000,
  ];

  return (
    setUnitText(numbers)
      .filter((number) => !!number)
      .join(" ") + "원"
  );
};

const setUnitText = (numbers) => {
  const unit = ["원", "만", "억", "조", "경"];
  return numbers.map((number, index) =>
    number ? numberFormat(number) + unit[unit.length - 1 - index] : number
  );
};

const numbering = (value, division) => {
  const result = Math.floor(value / division);
  return result === 0 ? null : result % division;
};

const NUMBER_FORMAT_REGX = /\B(?=(\d{3})+(?!\d))/g;

const numberFormat = (value) => {
  return value.toString().replace(NUMBER_FORMAT_REGX, ",");
};
function dateFormat(target) {
  let date = new Date(target);
  let dateFormatString =
    date.getFullYear() +
    "." +
    (date.getMonth() + 1 < 9
      ? "0" + (date.getMonth() + 1)
      : date.getMonth() + 1) +
    "." +
    (date.getDate() < 9 ? "0" + date.getDate() : date.getDate());
  return dateFormatString;
}


function callSwal({ title, text, icon }) {
  // icon
  // https://sweetalert2.github.io/#icons
  let msg = {
    icon: "info",
    title: "실패",
    text: "문제가 발생했습니다.",
    confirmButtonText: "확인",
    confirmButtonColor: "#00b4d8",
  };

  if (title) msg.title = title;
  if (text) msg.text = text;
  if (icon) msg.icon = icon;

  Swal.fire(msg);
}

export { moneyFormat, dateFormat, adjustedMoneyFormat, callSwal };
