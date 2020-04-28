let deletegoods = (event) => {
	let check = confirm('選択した商品を削除しますがよろしいですか？');
	
	if (check) {
		event.preventDefault();
		let tdList = $(event.target).parent().parent().find('td');
		let id = $(tdList[0]).text();
		
		let jsonString = {
				'id': id,
				'goodsName': $(tdList[1]).text(),
				'price': ''
		};
		
		$.ajax({
			type: 'POST',
			url: '/ecsite/admin/api/deleteGoods',
			data: JSON.stringify(jsonString),
			contentType: 'application/json',
			scriptCharset: 'utf-8'
		})
		
		.then((result) => {
			console.log(result);
			alert(`商品 [ ${jsonString.goodsName} ] を削除しました。`);
			$(tdList).parent().remove();
			
		}, () => {
			console.error('Error: ajax connection failed.');
		});
		
	} else {
	}
};

let login = (event) => {
	event.preventDefault();
	let jsonString = {
		'userName': $('input[name=userName]').val(),
		'password': $('input[name=password]').val()
	};
	
	$.ajax({
		type: 'POST',
		url: '/ecsite/api/login',
		data: JSON.stringify(jsonString),
		contentType: 'application/json',
		datatype: 'json',
		scriptCharset: 'utf-8'
	})

	.then((result) => {
		let user = JSON.parse(result);
		
		if (`${user.fullName}` === "ゲスト") {
			alert('ユーザー名、もしくはパスワードが間違っています。');
			$('input[name=userName]').val('');
			$('input[name=password]').val('');
			
		} else {
			$('#welcome').text(`${user.fullName}でログイン中`);
			$('#member_name').text(`${user.fullName}様`);
			$('#hiddenUserId').val(user.id);
			$('input[name=userName]').val('');
			$('input[name=password]').val('');
			
		}
	}, () => {
		console.error('ERROR: ajax connection failed.');
	});
};

let tag = 0;
let addCart = (event) => {
	let tdList = $(event.target).parent().parent().find('td');
	let id = $(tdList[0]).text();
	let goodsName = $(tdList[1]).text();
	let price = $(tdList[2]).text();
	let count = $(tdList[3]).find('input').val();
	
	if (count === '0' || count === '') {
		alert('注文数が0、または空欄です。')
		
		return;
	}
	tag = tag + 1;
	
	let cart = {
			'id': id,
			'goodsName': goodsName,
			'price': price,
			'count': count,
			'tag': tag
	};

	cartList.push(cart);

	let tbody = $('#cart').find('tbody');
	$(tbody).children().remove();
	cartList.forEach(function(cart, index) {
		let tr = $('<tr />');
		
		$('<td />', { 'text': cart.id }).appendTo(tr);
		$('<td />', { 'text': cart.goodsName }).appendTo(tr);
		$('<td />', { 'text': cart.price }).appendTo(tr);
		$('<td />', { 'text': cart.count }).appendTo(tr);
		let tdButton = $('<td />');
		$('<button />', {'text': 'カート削除',　'class': 'removeBtn'}).appendTo(tdButton);
		
		$(tdButton).appendTo(tr);
		$(tr).appendTo(tbody);

		$('<td />', { 'text': cart.tag }).appendTo(tr);
		
		});
		
		$('.removeBtn').on('click', removeCart);
};

let buy = (event) => {
	if (cartList.length == 0) {
		
		alert('商品が入っていません');
		
	} else {
		
		$.ajax({
			type: 'POST',
			url: '/ecsite/api/purchase',
			data: JSON.stringify({
				"userId": $('#hiddenUserId').val(),
				"cartList": cartList
			}),
			contentType: 'application/json',
			datatype: 'json',
			scriptCharset: 'utf-8'
		})
		
		.then((result) => {
			let tbody = $('#cart').find('tbody');
			$(tbody).children().remove();
			//cartListを初期化
			cartList = [];
			alert('購入しました');
			
		}, () => {
			console.error('ERROR: ajax connection failed.');
		});
	}
};

let showHistory = () => {
	$.ajax({
		type: 'POST',
		url: '/ecsite/api/history',
		data: JSON.stringify({
			"userId": $('#hiddenUserId').val(),
			"historySystem": $(event.target).val()
		}),
		contentType: 'application/json',
		datatype: 'json',
		scriptCharset: 'utf-8'
	})
	
	.then((result) => {
		let historyList = JSON.parse(result);
		let tbody = $('#historyTable').find('tbody');
		$(tbody).children().remove();
		historyList.forEach((history, index) => {
			let tr = $('<tr />');
			$('<td />', { 'text': history.goodsName }).appendTo(tr);
			$('<td />', { 'text': history.itemCount }).appendTo(tr);
			$('<td />', { 'text': history.createdAt }).appendTo(tr);
			$(tr).appendTo(tbody);
		});
		
		$("#history").dialog("open");
		
	}, () => {
		console.error('Error: ajax connection failed.');
	});
};

let removeCart = (event) => {
	const tdList = $(event.target).parent().parent().find('td');
	tag = $(tdList[5]).text();

	cartList = cartList.filter(function(cart) {
		return cart.tag != tag;
	})

	$(tdList).remove();
};