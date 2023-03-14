showInput();
getTeachers();

// handlebars 확장 태그 등록
Handlebars.registerHelper("degreeLabel", function (options) {
  return getDegreeLabel(parseInt(options.fn(this)));
});

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

function getTeachers(keyword) {
  let qs = "";
  if (keyword) {
    qs = `?keyword=${keyword}`;
  }

  fetch("../admin/teachers" + qs)
    .then((response) => {
      return response.json();
    })
    .then((result) => {
      document.querySelector("#teacher-table > tbody").innerHTML =
        templateEngine(result.data);
    });
}

function getDegreeLabel(degree) {
  switch (degree) {
    case 1:
      return "고졸";
    case 2:
      return "전문학사";
    case 3:
      return "학사";
    case 4:
      return "석사";
    case 5:
      return "박사";
    default:
      return "기타";
  }
}

function getTeacher(e) {
  let no = e.currentTarget.getAttribute("data-no");

  fetch("../admin/teachers/" + no)
    .then((response) => {
      return response.json();
    })
    .then((result) => {
      if (result.status == "failure") {
        alert("강사를 조회할 수 없습니다.");
        return;
      }

      let teacher = result.data;
      document.querySelector("#f-no").value = teacher.no;
      document.querySelector("#f-name").value = teacher.name;
      document.querySelector("#f-email").value = teacher.email;
      document.querySelector("#f-tel").value = teacher.tel;
      document.querySelector("#f-degree").value = teacher.degree;
      document.querySelector("#f-school").value = teacher.school;
      document.querySelector("#f-major").value = teacher.major;
      document.querySelector("#f-wage").value = teacher.wage;
      document.querySelector("#f-createdDate").innerHTML = teacher.createdDate;

      showEdit();

      const modal = new bootstrap.Modal("#teacherModal", {});
      modal.show();
    });
}

document.querySelector("#btn-insert").onclick = () => {
  const form = document.querySelector("#teacher-form");
  const formData = new FormData(form);

  let json = JSON.stringify(Object.fromEntries(formData));

  fetch("../admin/teachers", {
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
  const form = document.querySelector("#teacher-form");
  const formData = new FormData(form);

  let json = JSON.stringify(Object.fromEntries(formData));

  fetch("../admin/teachers/" + document.querySelector("#f-no").value, {
    method: "PUT",
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
  fetch("../admin/teachers/" + document.querySelector("#f-no").value, {
    method: "DELETE",
  })
    .then((response) => {
      return response.json();
    })
    .then((result) => {
      if (result.status == "success") {
        location.reload();
      } else {
        alert("강사 삭제 실패!");
      }
    })
    .catch((exception) => {
      alert("강사 삭제 중 오류 발생!");
      console.log(exception);
    });
};

document.querySelector("#btn-cancel").onclick = () => {
  showInput();
};

document.querySelector("#btn-new").onclick = () => {
  const modal = new bootstrap.Modal("#teacherModal", {});
  modal.show();
};
