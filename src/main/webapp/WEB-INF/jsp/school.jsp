<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>最新QS世界大学排名</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="stylesheet"
			href="<%=basePath%>bootstrap/css/bootstrap.min.css">
		<link type="text/css"
			href="<%=basePath%>bootstrap/css/bootstrap-responsive.min.css"
			rel="stylesheet">
		<link type="text/css" href="<%=basePath%>index/css/theme.css"
			rel="stylesheet">
		<link type="text/css"
			href="<%=basePath%>index/images/icons/css/font-awesome.css"
			rel="stylesheet">
		<link type="text/css"
			href='http://fonts.googleapis.com/css?family=Open+Sans:400italic,600italic,400,600'
			rel='stylesheet'>

		<style>
		body {
			background-color: white;
		}
		.loader {
			border: 16px solid #f3f3f3;
			border-radius: 50%;
			border-top: 16px solid #3498db;
			width: 10px;
			height: 10px;
			-webkit-animation: spin 2s linear infinite; /* Safari */
			animation: spin 2s linear infinite;
		}
		
		/* Safari */
		@-webkit-keyframes spin {
		    0% { -webkit-transform: rotate(0deg); }
		    100% { -webkit-transform: rotate(360deg); }
		}
		
		@keyframes spin {
		    0% { transform: rotate(0deg); }
		    100% { transform: rotate(360deg); }
		}
		
		.loading {
			position: absolute;
			left: 0;
			top: 0;
			z-index: 1000;
			width: 100%;
			height: 100%;
			min-height: 100%;
			background: white;
			opacity: 0.8;
			text-align: center;
			color: Teal;
		}
		
		.loading_anim {
			position: absolute;
			display: flex;
			left: 50%;
			top: 50%;
			transform: translate(-50%, -50%);
			z-index: 1010;
		}
		
		.dropdown-menu{
			display:block;
		}
	</style>
	</head>
	<body ng-app="myApp" ng-controller="myCtrl">
		<div class="content" ng-show="listShow">
			<div class="content">
				<div class="module">
					<div class="module-head">
						<h3>
							学校信息
						</h3>
					</div>
					<div class="module-body table">
						<div id="DataTables_Table_0_wrapper" class="dataTables_wrapper"
							role="grid">
							<div id="DataTables_Table_0_length" class="dataTables_length">
								<label> 搜索
									<select size="1" name="DataTables_Table_0_length"
										aria-controls="DataTables_Table_0" ng-change="updateType()" ng-model="selectedItem">
											<option value="">---请选择---</option>
											<option ng-repeat="item in types" value="{{ item }}">{{ item }}</option>
									</select> 
									<a class="btn btn-success" ng-click="addItem()">添加</a> 
									<a class="btn btn-success" href="school/initXSL.do">导入excel</a>
								</label>
							</div>
							<table class="table table-striped table-bordered table-condensed">
								<thead>
									<tr>
										<th>
											ID
										</th>
										<th>
											排名
										</th>
										<th>
											学校名称
										</th>
										<th>
											国家
										</th>
										<th>
											年份
										</th>
										<th>
											类型
										</th>
										<th>
											操作
										</th>
									</tr>
								</thead>
								<tbody>
									<tr ng-repeat="item in items">
										<td>
											{{ item.id }}
										</td>
										<td>
											{{ item.ranking }}
										</td>
										<td>
											{{ item.name }}
										</td>
										<td>
											{{ item.country }}
										</td>
										<td>
											{{ item.year }}
										</td>
										<td>
											{{ item.type }}
										</td>
										<td>
											<a class="btn btn-primary"
												ng-click="showUpdItem(item)">修改</a>
											<a class="btn btn-danger" ng-click="delItem(item.id)">删除</a>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<!--/.module-->
			</div>
		</div>
		<!-- area add -->
		<div class="content" ng-show="addShow">
			<div class="module">
				<div class="module-head">
					<h3>
						学校
					</h3>
				</div>
				<div class="module-body">
					<div class="alert alert-error" ng-show="addError">
						<strong>错误:</strong> <div>{{err}}</div>
					</div>
					<form class="form-horizontal row-fluid">
						<div class="control-group">
							<label class="control-label" for="basicinput">
								排名
							</label>
							<div class="controls">
								<input type="text" ng-model="itemRanking" placeholder="请填写学校排名"
									class="span8 tip">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="basicinput">
								学校名称
							</label>
							<div class="controls">
								<input type="text" ng-model="itemName" placeholder="请填写学校名称"
									class="span8 tip">
								<input type="hidden" ng-model="itemId">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="basicinput">
								国家
							</label>
							<div class="controls">
								<input type="text" ng-model="itemCountry" placeholder="请填写国家"
									class="span8 tip">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="basicinput">
								年份
							</label>
							<div class="controls">
								<input type="number" ng-model="itemYear" placeholder="请填写年份"
									class="span8 tip">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="basicinput">
								类型
							</label>
							<div class="controls">
								<input type="text" ng-model="itemType" placeholder="请填写类型"
									class="span8 tip">
							</div>
						</div>
						<div class="control-group">
							<div class="controls">
								<button class="btn" type="submit" ng-click="addItemSub()">
									提交
								</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<!-- loading -->
		<div class="loading" ng-show="loadingShow">
			<div class="loading_anim">
				<div class="loader" style="flex-grow: 1;"></div>
				<div
					style="flex-grow: 1; text-align: center; line-height: 52px; height: 52px;">
					数据提交中请稍后
				</div>
			</div>
		</div>
		<script src="<%=basePath%>js/angular1.4.8.min.js"
			type="text/javascript"></script>
		<script type="text/javascript">
			//初始化List信息
			function initListData($http,$scope,schoolType){
				var $schoolType=schoolType==undefined?null:schoolType;
			    $http({
			        method : "GET",
			        url : "<%=basePath%>school/getSchoolList.do",
				       params: {schoolType:$schoolType}
			    }).then(function mySucces(response) {
			    	$scope.loadingShow = false;
			        $scope.items = response.data.datas;
			    }, function myError(response) {
			    	$scope.loadingShow = false;
			        alert("school->getSchoolList.do访问错误出错!");
			        console.log(response.statusText);
			    });
			}
			
			//初始化类型信息
			function initTypes($http,$scope){
			    $http({
			        method : "GET",
			        url : "<%=basePath%>school/getTypes.do"
			    }).then(function mySucces(response) {
			    	$scope.loadingShow = false;
			        $scope.types = response.data.datas;
			    }, function myError(response) {
			    	$scope.loadingShow = false;
			        alert("school->getTypes.do访问错误出错!");
			        console.log(response.statusText);
			    });
			}
			
			var app = angular.module('myApp', []);
			app.controller('myCtrl', function($scope,$http) {
				//初始化学校信息
			    initListData($http,$scope);
			  	//初始化类型信息
				initTypes($http,$scope);
			    //隐藏加载框
				$scope.loadingShow = true;
				//学校信息
			    $scope.listShow = true;
			    $scope.addShow = false;
			    $scope.addError = false;
			    $scope.err = '';
			    $scope.itemName = '';
			    $scope.itemRanking = '';
			    $scope.itemName = '';
			    $scope.itemCountry = '';
			    $scope.itemYear = '';
			    $scope.itemType = '';
			    //添加区域信息
			    $scope.addItem = function() {
			        $scope.addShow = true;
				    $scope.listShow = false;
				    $scope.addError = false;
				    $scope.itemId="";
				    $scope.itemRanking = '';
				    $scope.itemName = '';
				    $scope.itemCountry = '';
				    $scope.itemYear = '';
				    $scope.itemType = '';
			    };
			    //添加学校提交
			    $scope.addItemSub = function() {
			    	if($scope.itemRanking ==null||$scope.itemRanking == ''){
			    		$scope.err = '请填写排名';
			        	$scope.addError = true;
			        	return;
			        }
			        if($scope.itemName ==null||$scope.itemName == ''){
			        	$scope.err = '请填写学校名称';
			        	$scope.addError = true;
			        	return;
			        }
			        if($scope.itemCountry==null||$scope.itemCountry == ''){
			        	$scope.err = '请填写国家';
			        	$scope.addError = true;
			        	return;
			        }
			        if($scope.itemYear==null||$scope.itemYear == ''){
			        	$scope.err = '请填写年份';
			        	$scope.addError = true;
			        	return;
			        }
			        if($scope.itemType==null||$scope.itemType == ''){
			        	$scope.err = '请填写类型';
			        	$scope.addError = true;
			        	return;
			        }
			        $scope.addError = false;
		        	$scope.loadingShow = true;
		        	//排名
		        	var itemRanking = $scope.itemRanking;
		        	//学校名称
		        	var itemName = $scope.itemName;
		        	itemName = encodeURI(itemName);
		        	//国家名称
		        	var itemCountry = $scope.itemCountry;
		        	itemCountry = encodeURI(itemCountry);
		        	//年份
		        	var itemYear = $scope.itemYear;
		        	//类型
		        	var itemType = $scope.itemType;
		         	if($scope.itemId==""){
		         		//增加学校信息
					    $http({
					        method : "POST",
					        url : "<%=basePath%>school/addItem.do",
					        params: {
					        	ranking:itemRanking,
					        	name:itemName,
					        	country:itemCountry,
					        	year:itemYear,
					        	type:itemType
							}
					    }).then(function mySucces(response) {
					        if(response.data.code==0){
					            //初始化区域信息
			    				initListData($http,$scope);
							    $scope.addShow = false;
				    			$scope.listShow = true;
					        	alert('添加成功!');
					        }else{
					        	$scope.loadingShow = false;
					        	alert('添加失败!');
					        }
					        $scope.itemId="";
						    $scope.itemRanking = '';
						    $scope.itemName = '';
						    $scope.itemCountry = '';
						    $scope.itemYear = '';
						    $scope.itemType = '';
					    }, function myError(response) {
					        alert("addItemSub.do访问错误出错!");
					        console.log(response.statusText);
					    });
		         	}else{
		         		var id = $scope.itemId;
		         		//修改学校信息
					    $http({
					        method : "PUT",
					        url : "<%=basePath%>school/updItem.do",
					        params: {
					        	ranking:itemRanking,
					        	name:itemName,
					        	country:itemCountry,
					        	year:itemYear,
					        	type:itemType,
								id:id
							}
					    }).then(function mySucces(response) {
					        if(response.data.code==0){
					            //初始化信息
			    				initListData($http,$scope);
							    $scope.addShow = false;
				    			$scope.listShow = true;
					        	alert('修改成功!');
					        }else{
					        	$scope.loadingShow = false;
					        	alert('修改失败!');
					        }
					        $scope.itemId="";
						    $scope.itemRanking = '';
						    $scope.itemName = '';
						    $scope.itemCountry = '';
						    $scope.itemYear = '';
						    $scope.itemType = '';
					    }, function myError(response) {
					        alert("updArea.do访问错误出错!");
					        console.log(response.statusText);
					    });
		         	}
			    };
			    //删除学校信息
			    $scope.delItem = function(id) {
			    	if (window.confirm("是否删除选中项?")) {
			    		$scope.loadingShow = true;
						$http({
					        method : "POST",
					        url : "<%=basePath%>school/delItem.do",
					        params: {
								id:id,
								"_method":"delete"
							}
					    }).then(function mySucces(response) {
					        if(response.data.code==0){
					            $scope.itemName = '';
					            //初始化信息
				    			initListData($http,$scope);
					        	alert('删除成功!');
					        }else{
					        	$scope.loadingShow = false;
					        	alert('删除失败!');
					        }
					    }, function myError(response) {
					        alert("delItem.do访问错误出错!");
					        console.log(response.statusText);
					    });
					}
			    };
			    //显示修改学校信息
			    $scope.showUpdItem = function(item) {
			    	$scope.addShow = true;
				    $scope.listShow = false;
				    $scope.itemId = item.id;
				    $scope.itemRanking = item.ranking;
				    $scope.itemName = item.name;
				    $scope.itemCountry = item.country;
				    $scope.itemYear = item.year;
				    $scope.itemType = item.type;
			    };
			  	//改变类型信息
			    $scope.updateType = function() {
			  		var selectedItem = $scope.selectedItem;
					if(selectedItem==""){
						initListData($http,$scope);
					}else{
						initListData($http,$scope,selectedItem);
					}
			  	}
			});
		</script>
	</body>
</html>
