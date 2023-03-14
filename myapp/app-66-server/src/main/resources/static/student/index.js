showInput();
getStudents();

const html = document.querySelector("#tr-template").innerHTML;
const templateEngine = Handlebars.compile(html);

function showInput() {
  let el = document.querySelectorAll(".input");
  for (let e of el) {
    e.classList.remove("invisible");
  }

  el = document.querySelectorAll(".edit");
  for (let e of el) {
    e.classList.add("invisible");
  }
}

function showEdit() {
  let el = document.querySelectorAll(".input");
  for (let e of el) {
    e.classList.add("invisible");
  }

  el = document.querySelectorAll(".edit");
  for (let e of el) {
    e.classList.remove("invisible");
  }
}

document.querySelector("input[name='keyword']").onkeyup = (e) => {
  getStudents(e.target.value);
};

document.querySelector("#btn-search").onclick = () => {
  getStudents(keyword);
};

function getStudents(keyword) {
  let qs = "";
  if (keyword) {
    qs = `?keyword=${keyword}`;
  }

  fetch("../students" + qs)
    .then((response) => {
      return response.json();
    })
    .then((result) => {
      document.querySelector("#student-table > tbody").innerHTML =
        templateEngine(result.data);
    });
}

function getLevelTitle(level) {
  switch (level) {
    case 0:
      return "비전공자";
    case 1:
      return "준전공자";
    case 2:
      return "전공자";
    default:
      return "기타";
  }
}

function getStudent(e) {
  let no = e.currentTarget.getAttribute("data-no");

  fetch("../students/" + no)
    .then((response) => {
      return response.json();
    })
    .then((result) => {
      if (result.status == "failure") {
        alert("학생을 조회할 수 없습니다.");
        return;
      }

      let student = result.data;
      console.log(student);
      document.querySelector("#f-no").value = student.no;
      document.querySelector("#f-name").value = student.name;
      document.querySelector("#f-email").value = student.email;
      document.querySelector("#f-tel").value = student.tel;
      document.querySelector("#f-postNo").value = student.postNo;
      document.querySelector("#f-basicAddress").value = student.basicAddress;
      document.querySelector("#f-detailAddress").value = student.detailAddress;
      document.querySelector("#f-working").checked = student.working;
      document.querySelector(
        `input[name="gender"][value="${student.gender}"]`
      ).checked = true;
      document.querySelector("#f-level").value = student.level;
      document.querySelector("#f-createdDate").innerHTML = student.createdDate;

      showEdit();
    });
}

document.querySelector("#btn-insert").onclick = () => {
  const form = document.querySelector("#student-form");
  const formData = new FormData(form);

  let json = JSON.stringify(Object.fromEntries(formData));

  fetch("../students", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: json,
  })
    .then((response) => {
      return response.json();
    })
    .then((result) => {
      if (result.status == "success") {
        location.reload();
      } else {
        alert("입력 실패!");
        console.log(result.data);
      }
    })
    .catch((exception) => {
      alert("입력 중 오류 발생!");
      console.log(exception);
    });
};

document.querySelector("#btn-update").onclick = () => {
  const form = document.querySelector("#student-form");
  const formData = new FormData(form);

  // FormData ==> Query String
  // 방법1)
  //let qs = [...formData.entries()].map(x => `${encodeURIComponent(x[0])}=${encodeURIComponent(x[1])}`).join('&');
  // 방법2)
  //let qs = new URLSearchParams(formData).toString();
  //console.log(qs);

  let json = JSON.stringify(Object.fromEntries(formData));
  //console.log(json);

  fetch("../students/" + document.querySelector("#f-no").value, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
      //"Content-Type": "application/x-www-form-urlencoded"
    },
    //body: formData
    body: json,
    //body: qs
  })
    .then((response) => {
      return response.json();
    })
    .then((result) => {
      if (result.status == "success") {
        alert("변경 했습니다.");
        location.reload();
      } else {
        alert("변경 실패!");
        console.log(result.data);
      }
    })
    .catch((exception) => {
      alert("변경 중 오류 발생!");
      console.log(exception);
    });
};

document.querySelector("#btn-delete").onclick = () => {
  fetch("../students/" + document.querySelector("#f-no").value, {
    method: "DELETE",
  })
    .then((response) => {
      return response.json();
    })
    .then((result) => {
      if (result.status == "success") {
        location.reload();
      } else {
        alert("학생 삭제 실패!");
      }
    })
    .catch((exception) => {
      alert("학생 삭제 중 오류 발생!");
      console.log(exception);
    });
};

document.querySelector("#btn-cancel").onclick = () => {
  showInput();
};

// entries ==> query string
function toQueryStringFromEntries(entries) {
  let qs = "";
  for (let [key, value] of entries) {
    if (qs.length > 0) {
      qs += "&";
    }
    qs += encodeURIComponent(key) + "=" + encodeURIComponent(value);
  }
  return qs;
}

function toQueryStringFromEntries2(entries) {
  let arr = [];
  for (let entry of entries) {
    arr.push(entry);
  }

  //console.log(arr);

  let arr2 = arr.map(
    (x) => `${encodeURIComponent(x[0])}=${encodeURIComponent(x[1])}`
  );
  //console.log(arr2);

  let str = arr2.join("&");
  //console.log(str);

  return str;
}

function toQueryStringFromEntries3(entries) {
  let arr = [...entries];

  //console.log(arr);

  let arr2 = arr.map(
    (x) => `${encodeURIComponent(x[0])}=${encodeURIComponent(x[1])}`
  );
  //console.log(arr2);

  let str = arr2.join("&");
  //console.log(str);

  return str;
}
