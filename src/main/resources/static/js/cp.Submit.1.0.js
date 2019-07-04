var url = {};
var thisWindowIndex = parent.layer.getFrameIndex(window.name) || null;
var cpSubmit = function (url, elem) {
	// 提交数据
	this.data = {};
	// 初始化数据
	this.initData = {};
	// 备份数据，最后一次有效数据的备份
	this.tempData = {};
	// 安全数据，用于界面回滚，包括全部数据
	this.safeData = {};
	// 字段集合
	this.fields = {};
	// 分页信息
	this.pages = {
		total: 0,//总条数
		lastPage: 1,//共几页
		currentPage: 1,//当前页
		perPage: 0//每页几条
	};
	// 自定义方法
	this.customMethod = function () {
	};
	this.elem = elem;
	this.url = url;
};

/**
 * 设置提交数据
 * @param {string} key 字段名
 * @param {string} value 值
 */
cpSubmit.prototype.setData = function (key, value) {
	this.data[key] = value;
};

/**
 * 返回要提交的数据
 */
cpSubmit.prototype.getData = function () {
	return this.data;
};

/**
 * 自动采集数据
 * @param className 采集class名称
 * @param elem 采集dom范围
 */
cpSubmit.prototype.autoCollection = function (className, elem) {
	elem = elem ? elem : this.elem;
	className = className ? className : 'cp-auto';
	var _the = this;
	// 通用
	(function () {
		var tag = ['select', 'textarea', 'input:text', 'input:password', 'input:hidden'];
		for (var i = 0; i < tag.length; i++) {
			tag[i] += '.' + className;
		}
		var selector = tag.join();
		elem.find(selector).each(function () {
			var key = $(this).attr('name');
			var val = $(this).val();
			if (key === '' || key === null || key === undefined || key === 'null' || key === 'undefined') {
				alert('自动采集错误');
				_the.data = {};
				return false;
			}
			_the.setData(key, val);
		});
	})();
	// 单选
	(function () {
		var names = elem.find('input:radio.' + className).map(function () {
			return $(this).attr('name');
		}).toArray();
		$.unique(names);
		$.each(names, function (i, name) {
			if (name === '' || name === null || name === undefined || name === 'null' || name === 'undefined') {
				alert('自动采集错误');
				_the.data = {};
				return false;
			}
			_the.setData(name, elem.find('input:radio[name="' + name + '"]:checked:last').attr('value') || '');
		});
	})();
	// 复选
	(function () {
		var names = elem.find('input:checkbox.' + className).map(function () {
			return $(this).attr('name');
		}).toArray();
		$.unique(names);
		$.each(names, function (i, name) {
			if (name === '' || name === null || name === undefined || name === 'null' || name === 'undefined') {
				alert('自动采集错误');
				_the.data = {};
				return false;
			}
			elem.find('input:checkbox[name="' + name + '"]:checked').map(function () {
				_the.setData(name, $(this).attr('value'));
			}).toArray();
		});
	})();

	//备份数据
	_the.tempData = _the.data;
};

/**
 * 表单提交数据
 * @param data 需要提交的数据
 * @param url 提交地址
 * @param trend 请求成功后是否刷新页面
 * @param func 自定义请求成功后执行方法
 * @param method 为true代表不弹出提示，只执行func回掉。届时func参数必须存在且为一个方法
 */
cpSubmit.prototype.success = function (data, url, trend, func, method) {
	$.ajax({
		type: 'POST',
		url: url ? url : this.url,
		data: data ? data : this.data,
		success: function (response, status, xhr) {
			if (method === true) {
				if (typeof func === 'function') {
					func.call(this, response, xhr);
				} else {
					layer.alert('加载失败');
				}
			} else {
				if (response.code == 1) {
					layer.msg(response.msg, {
						shade: 0.1,
						shadeClose: true
					}, function (e) {
						layer.close(e);
						if (typeof func === 'function') {
							func.call(this, response);
						}
						if (response.url && response.data == 'jump') {
							window.location.href = response.url;
							return false;
						}
						if (trend === true) {
							window.location.reload();
						}
					});
				} else {
					layer.alert(response.msg || '服务器无响应，稍后请重试。。。', {
						icon: 7,
						title: '出错了',
						closeBtn: false
					}, function (e) {
						layer.close(e);
						if (response.url && response.data == 'jump') {
							window.location.href = response.url;
							return false;
						}
					});
				}
			}
		},
		error: function () {
			layer.alert('请求失败');
		}
	});
};

/**
 * 初始化列表
 */
cpSubmit.prototype.initList = function (dom, func, data, url, trend) {
	var the = this,
		elem = the.elem;
	if (!dom && !elem) {
		layer.alert('未定义列表加载位置');
		return false;
	}
	elem = dom ? dom : elem;
	the.loadList(elem, func, data, url, trend);
	the.initPage();
	// 绑定搜索事件
	elem.on('click', '#cp-search-submit', function () {
		the.setData('page', 1);
		the.loadList(elem, func, data, url, trend);
	});
};

/**
 * 加载列表
 * @param dom 需要加载的位置
 * @param data 需要提交的数据
 * @param url 提交地址
 * @param trend 请求成功后是否刷新页面
 * @param func 自定义请求成功后执行方法
 */
cpSubmit.prototype.loadList = function (dom, func, data, url, trend) {
	var the = this,
		elem = the.elem;
	if (!dom && !elem) {
		layer.alert('未定义列表加载位置');
		return false;
	}
	elem = dom ? dom : elem;
	the.autoCollection();
	the.success(data, url, trend ? trend : false, func ? func : function (row, xhr) {
		elem.find('#cp-table').html(row);
		the.setPageInfo($.parseJSON(xhr.getResponseHeader('pageInfo')));
	}, true);
};

/**
 * 初始化分页点击事件
 */
cpSubmit.prototype.initPage = function () {
	var the = this,
		elem = the.elem;
	elem.on('click', '.cp-pg-firstPage', function () {
		// 第一页
		the.setData('page', 1);
		the.loadList();
	}).on('click', '.cp-pg-prevPage', function () {
		// 上一页
		the.setData('page', --the.pages.currentPage);
		the.loadList();
	}).on('click', '.cp-pg-nextPage', function () {
		// 下一页
		the.setData('page', ++the.pages.currentPage);
		the.loadList();
	}).on('click', '.cp-pg-lastPage', function () {
		// 最后一页
		the.setData('page', the.pages.lastPage);
		the.loadList();
	}).on('click', '.cp-pg-jump', function () {
		// 页码跳转
		var num = elem.find('.cp-pg-text').val();
		var pageNum = num;
		if (num > the.pages.lastPage) {
			layer.msg('您输入了第' + num + '页，但是最多只有' + the.pages.lastPage + '页', {offset: 'rb'});
			pageNum = the.pages.lastPage;
		} else if (num < 1) {
			layer.msg('您输入了第' + num + '页，但是至少翻至第1页', {offset: 'rb'});
			pageNum = 1;
		}
		elem.find('.cp-pg-text').val(pageNum);
		the.setData('page', pageNum);
		the.loadList();
	}).on('keyup', '.cp-pg-text', function (e) {
		var thi = $(this),
			v = thi.val();
		thi.val(v.replace(/\D|^0/g, ''));
		if (v < 1) {
			thi.val(1);
		}
		if (parseInt(v) > parseInt(the.pages.lastPage)) {
			thi.val(parseInt(the.pages.lastPage));
		}
	});
};

/**
 * 加载分页信息
 * @param pageInfo 分页信息
 total: 0,//总条数
 lastPage: 1,//共几页
 currentPage: 1,//当前页
 perPage: 0//每页几条
 */
cpSubmit.prototype.setPageInfo = function (pageInfo) {
	var the = this,
		elem = the.elem,
		page = $.extend(the.pages, pageInfo);
	the.setData('page', page.currentPage);
	elem.find('.cp-pg-total').text(page.total);
	elem.find('.cp-pg-perpage').text(page.perPage);
	elem.find('.cp-pg-currentpage').text(page.currentPage);
	elem.find('.cp-pg-lastpage').text(page.lastPage);
	elem.find('.cp-pg-text').val(page.currentPage);
	elem.find('.cp-pg-firstPage,.cp-pg-prevPage').prop('disabled', page.currentPage <= 1);
	elem.find('.cp-pg-nextPage,.cp-pg-lastPage').prop('disabled', page.currentPage >= page.lastPage);
	elem.find('.cp-pg-text,.cp-pg-jump').prop('disabled', page.lastPage <= 1);
};

//全局事件委派
$(function () {
	// 加载动画
	var loadingIndex,
		ajaxFlag = 0,
		dialogStatus = 0,
		loadingToggle = function (status) {
			if (true === status) {
				ajaxFlag++;
			} else {
				ajaxFlag--;
			}
			if (ajaxFlag > 0) {
				if (dialogStatus == 0) {
					loadingIndex = layer.load(0, {shade: false});
					dialogStatus = 1;
				}
			} else {
				layer.close(loadingIndex);
				dialogStatus = 0;
			}
		};
	$(document).ajaxSend(function () {
		loadingToggle(true);
	}).ajaxComplete(function () {
		loadingToggle(false);
	});

	if (thisWindowIndex) {
		$('.cp-close').show();
	}

	$(document).on('click', '.cp-open', function (e) {
		var the = $(this),
			that = this,
			url = the.data('url') || the.attr('href');
		e.preventDefault();
		if (url) {
			var title = the.data('title'),
				resize = the.data('resize'),//是否可拉伸
				width = the.data('width') ? the.data('width') : '100%',
				height = the.data('height') ? the.data('height') : '100%';
			layer.open({
				type: 2,
				title: title ? title : false,
				closeBtn: title ? 1 : 0,
				shadeClose: false,
				shade: 0.8,
				scrollbar: false,
				move: false,
				resize: resize ? resize : false,
				area: [width, height],
				content: url,
				end: function () {
					var endFunc = the.data('end');
					if ($.type(endFunc) == 'function') {
						endFunc.call(that);
					}
				}
			});
		} else {
			layer.alert('未指定窗口链接！');
		}

	}).on('click', '.cp-reload', function () {
		$(this).data('end', function () {
			console.log('自定义回掉事件');
		});
	}).on('click', '.cp-confirm', function (e) {
		e.preventDefault();
		var the = $(this),
			url = the.data('url') || the.attr('href'),
			title = the.data('title') || '确定执行此操作？';
		if (!the.hasClass('cp-ajax')) {
			if (url) {
				layer.alert(title, function (index) {
					window.location.href = url;
					layer.close(index);
				});
			} else {
				layer.alert('未指定窗口链接！');
			}
		}
	}).on('click', '.cp-close', function (e) {
		e.preventDefault();
		if (thisWindowIndex) {
			parent.layer.close(thisWindowIndex);
		}
	}).on('click', '.tips td', function (e) {
		var the = $(this).not(".not-tips"),
			_str = the.html();
		if (_str != undefined) {
			layer.tips(_str, the, {
				tips: [1, '#333'] //还可配置颜色
			});
		}
	});
});