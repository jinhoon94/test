window.onload = () => {
    const finalAmount = sessionStorage.getItem("finalAmount");
    const finalAmountOrder = sessionStorage.getItem("finalAmountOrder");

    const finalAmountElement = document.querySelector("#finalAmount1");
    if (finalAmountElement && finalAmount) {
        finalAmountElement.textContent = `₩ ${parseInt(finalAmount).toLocaleString()}`;
    }

    const finalAmountOrderElement = document.querySelector("#finalAmount2");
    if (finalAmountOrderElement && finalAmountOrder) {
        finalAmountOrderElement.textContent = `₩ ${parseInt(finalAmountOrder).toLocaleString()}`;
    }

    const sizeMap = {
        "싱글레귤러": 1,
        "싱글킹": 2,
        "더블주니어": 3,
        "더블레귤러": 4,
        "파인트": 5,
        "쿼터": 6,
        "패밀리": 7,
        "하프갤런": 8
    };

    const nowRaw = sessionStorage.getItem("now");
    const myDataRaw = sessionStorage.getItem("myData");

    let place_id = null;

    if (myDataRaw) {
        try {
            const myDataObj = JSON.parse(myDataRaw);
            place_id = myDataObj.place_id;
        } catch (err) {
            console.error("myData 파싱 오류:", err);
        }
    }

    // 오늘 날짜를 YYYY-MM-DD 형식으로 가져옴
    const currentDate = new Date();
    const currentDateString = currentDate.toISOString().split('T')[0];  // "YYYY-MM-DD" 형식
    console.log("현재 날짜:", currentDateString);

    // 로컬스토리지에서 저장된 날짜와 카운터 값 확인
    const storedDate = localStorage.getItem('lastVisitDate');
    let payCounter = localStorage.getItem('payCounter');
    console.log("저장된 날짜:", storedDate);
    console.log("저장된 카운터:", payCounter);

    // 날짜가 바뀌면 카운터 리셋
    if (storedDate !== currentDateString) {
        payCounter = 1;  // 카운터 초기화
        localStorage.setItem('lastVisitDate', currentDateString);  // 오늘 날짜 저장
        localStorage.setItem('payCounter', payCounter);  // 초기 카운터 저장
        console.log("날짜가 바뀌어서 카운터 초기화됨");
    } else {
        payCounter = parseInt(payCounter) || 1;  // 기존 카운터 유지
        console.log("기존 카운터 유지:", payCounter);
    }

    // 결제 번호 생성 (오늘 날짜 + 세자리수 카운터)
    const pay_num = `${currentDateString.replace(/-/g, '')}${payCounter.toString().padStart(3, '0')}`;
    console.log("생성된 결제 번호:", pay_num);

    // 카운터 증가 및 저장
    payCounter++;
    localStorage.setItem('payCounter', payCounter);

    console.log("증가된 카운터:", payCounter);

    // 버튼 클릭 이벤트
    const buttons = document.querySelectorAll(".aside2 button");
    buttons.forEach(button => {
        button.addEventListener("click", () => {
            const pay_method = "카드";

            if (!nowRaw) return;

            try {
                const nowObj = JSON.parse(nowRaw);
                const keys = Object.keys(nowObj);

                keys.forEach((key, index) => {
                    const nowItem = nowObj[key];
                    const size_id = sizeMap[nowItem.size] || null;
                    const amount = nowItem.count ?? 1;
                    const menuList = Array.isArray(nowItem.flavor)
                        ? nowItem.flavor.map(f => f.id)
                        : [];

                    // amount가 1보다 크면 결제를 amount번 반복해서 요청
                    for (let i = 0; i < amount; i++) {
                        // ✅ 결제 요청 (각 항목마다 개별 요청)
                        fetch(`http://127.0.0.1:8080/api/pay/payment`, {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json'
                            },
                            body: JSON.stringify({
                                size_id: size_id,
                                place_id: place_id,
                                pay_method: pay_method,
                                pay_num: pay_num,  // 여기서 pay_num 사용
                                menuList: menuList
                            })
                        }).then(response => {
                            console.log("결제 요청 성공:", response);
                        }).catch(err => {
                            console.error("결제 요청 실패:", err);
                        });

                        // ✅ 재고 차감 요청 (flavor 하나씩)
                        menuList.forEach(menu_id => {
                            fetch(`http://127.0.0.1:8080/api/sell`, {
                                method: 'PUT',
                                headers: {
                                    'Content-Type': 'application/json'
                                },
                                body: JSON.stringify({
                                    menu_id: menu_id,
                                    place_id: place_id,
                                    amount: 1  // 매번 1씩 차감 (여기서 amount는 재고 차감 수량)
                                })
                            }).catch(err => console.error("재고 차감 요청 실패:", err));
                        });
                    }
                });

            } catch (err) {
                console.error("now 값 파싱 오류:", err);
            }
        });
    });
};
