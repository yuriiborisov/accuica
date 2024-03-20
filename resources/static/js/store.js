
function defaultState() {
	return Object.create({
		currentVersion: '10', // Если нужно сбросить localStorage у пользователей, то нужно повысить версию
		localStorageVersion: '',
		loader: {
			isLoading: false,
			count: 0,
		},
		user: {
			username: '',
			regDate: '',
			role: Role.UNDEFINED,
			isAuth: false,
			rememberMe: false,
			authTime: null,
		},
		routeQuery: {
			partner: '',
			promoCode: '',
		},
		subscription: {
			expiredDate: '',
			subscriptionDate: '',
			payments: [],
		},
		promoCode: {
			id: '',
			tariffs: '',
			expired: '',
			discountType: '',
			discountValue: '',
		},
		screen: {
			width: '',
			height: '',
		},
		bunchesPage: {
			catalogues: {
				exchanges: [],
				assets: [],
				currencies: [],
				paymentMethods: {},
				purchaseMethods: [],
				countries: [],
			},
			filterForm: {
				isFirstFilterLoading: true,
				fiat: '',
				exchanges: [],
				assets: [],
				paymentMethods: [],
				purchaseMethods: [],
				tradeLimitFrom: '',
				currencyDeposit: '',
				minUserOrderNum: '',
				minUserOrderRate: '',
				minUserOrdersApplyTo: [],
				maxSpread: '',
				volume: '',
				usefulPositionsTo: '',
				positionsRemoveWithLimitTo: '',
				positionsPaymentMethods: [],
				filterTimer: '0',
			},
			usedDefaultFilter: {
				usedRole: '',
				usedFilter: '',
			},
			bunchesLoading: false,
			bunchesUpdated: null,
			isAnonymous: true,
			bunches: [],
			records: {},
		},
		classic3BunchesPage: {
			filterForm: {
				isFirstFilterLoading: true,
				fiat: '',
				exchanges: [],
				assets: [],
				paymentMethods: [],
				purchaseMethods: [],
				tradeLimitFrom: '',
				currencyDeposit: '',
				minUserOrderNum: '',
				minUserOrderRate: '',
				minUserOrdersApplyTo: [],
				maxSpread: '',
				volume: '',
				usefulPositionsTo: '',
				positionsRemoveWithLimitTo: '',
				positionsPaymentMethods: [],
				filterTimer: '0',
			},
			defaultFilter: {
				usedRole: '',
				usedFilter: '',
			},
			bunchesLoading: false,
			bunchesUpdated: null,
			isAnonymous: true,
			bunches: [],
			records: {},
		},
		intBunchesPage: {
			filterForm: {
				isFirstFilterLoading: true,
				fiat: 'RUB',
				currencyDeposit: '',
				countryTransfer: '',
				exchanges: [],
				assets: [],
				purchaseMethods: [],
				tradeLimitFrom: '',
				transferServices: [],
				conversionCurrency: '',
				conversionRate: '',
				foreignCurrency: '',
				foreignPayments: [],
				saleCurrency: '',
				salePayments: [],
				minUserOrderNum: '',
				minUserOrderRate: '',
				minUserOrdersApplyTo: [],
				usefulPositionsTo: '',
				positionsRemoveWithLimitTo: '',
				positionsPaymentMethods: [],
				filterTimer: '0',
			},
			defaultFilter: {
				usedRole: '',
				usedFilter: '',
			},
			bunchesLoading: false,
			bunchesUpdated: null,
			isAnonymous: true,
			bunches: [],
			records: {},
		},
		tariff: {
			catalogues: [],
		}
	});
}

const store = Vuex.createStore({
	strict: !isProduction, // TODO disable on production
	state() {
		return defaultState();
	},

	getters: {
		getUser(state) {
			return state.user;
		},

		isSubscriber(state) {
			return state.user.role === Role.SUBSCRIBER;
		},

		getScreenSize(state) {
			// xs -> 0 < x < 820
			// sm -> 820 < x < 1220
			// md -> 1220 < x < 1460
			// lg -> 1460 < x < 1920
			// xl -> 1920 < x < ∞

			const bootstrapBreakpoints = [
				{name: 'xs', widthTo: '820'},
				{name: 'sm', widthTo: '1220'},
				{name: 'md', widthTo: '1460'},
				{name: 'lg', widthTo: '1920'},
				{name: 'xl', widthTo: '10000'},
			];
			const screenSize = state.screen.width;
			const breakpoint = bootstrapBreakpoints.find(({widthTo}) => screenSize < widthTo);
			return breakpoint ? breakpoint : bootstrapBreakpoints[bootstrapBreakpoints.length - 1];
		},

		isTabletOrMobile(state, getters) {
			return getters.isMobile || getters.isTablet;
		},

		isTablet(state, getters) {
			return getters.getScreenSize.name === 'sm';
		},

		isMobile(state, getters) {
			return getters.getScreenSize.name === 'xs';
		},
	},

	actions: {

		[AC.INIT_APP_DATA]({dispatch}) {
			dispatch(AC.FETCH_LOCAL_STORAGE);
			dispatch(AC.CHECK_AUTH);
			dispatch(AC.FETCH_RECAPTCHA_KEY);
		},

		[AC.FETCH_LOCAL_STORAGE]({commit, dispatch, state}) {
			if (localStorage.getItem('store')) {
				const store = JSON.parse(localStorage.getItem('store'));

				if (store.localStorageVersion === state.currentVersion) {
					const target = {};
					useMergeDeep(target, state, store);
					this.replaceState(target);
				} else {
					const target = {};
					useMergeDeep(target, state, {user: store.user});
					this.replaceState(target);
				}
			}
		},

		[AC.TOGGLE_LOADING]({commit}, isLoading) {
			commit(MT.SET_LOADING, isLoading);
		},

		[AC.CHECK_AUTH]({commit, dispatch, state}) {
			commit(MT.SET_ROLE, Role.UNDEFINED);

			http.get('/users/' + state.user.username, {
				settings: {ignoreResultError: true}
			}).then(({data}) => {
				commit(MT.SET_USER, data);
				dispatch(AC.FETCH_SUBSCRIPTION_INFO);
				dispatch(AC.TOGGLE_AUTH, true);
			}).catch(err => {
				commit(MT.SET_ROLE, Role.ANONYMOUS);
			});
		},

		[AC.TOGGLE_AUTH]({commit}, isAuth) {
			commit(MT.SET_AUTH_STATUS, isAuth);
		},

		[AC.LOGOUT]({dispatch}) {
			http.get('/logout')
				.then()
				.catch()
				.then(() => {
					// always executed
					dispatch(AC.TOGGLE_AUTH, false);
					window.location.href = '/lk/login';
				});
		},

		[AC.FIND_BUNCHES]({commit, dispatch, getters}, {typeBunches, params}) {

			commit(MT.SET_BUNCHES_LOADING, {typeBunches, loading: true});

			http.get('/bunches', {
				params,
				settings: {loading: false}
			})
				.then(({data}) => {
					commit(MT.SET_BUNCHES, {
						typeBunches,
						bunches: data.bunches,
						records: data.records,
						isAnonymous: data.isAnonymous,
					});
				})
				.catch(err => {
				})
				.then(() => {
					commit(MT.SET_BUNCHES_LOADING, {typeBunches, loading: false});
				});
		},

		[AC.FIND_CLASSIC3_BUNCHES]({commit, dispatch, getters}, {typeBunches, params}) {

			commit(MT.SET_BUNCHES_LOADING, {typeBunches, loading: true});

			http.get('/bunches/classic3steps', {
				params,
				settings: {loading: false}
			})
				.then(({data}) => {
					commit(MT.SET_BUNCHES, {
						typeBunches,
						bunches: data.bunches,
						records: data.records,
						isAnonymous: data.isAnonymous,
					});
				})
				.catch(err => {
				})
				.then(() => {
					commit(MT.SET_BUNCHES_LOADING, {typeBunches, loading: false});
				});
		},

		[AC.FIND_INT_BUNCHES]({commit, dispatch, getters}, {typeBunches, params}) {

			commit(MT.SET_BUNCHES_LOADING, {typeBunches, loading: true});

			http.get('/bunches/international', {
				params,
				settings: {loading: false}
			})
				.then(({data}) => {
					commit(MT.SET_BUNCHES, {
						typeBunches,
						bunches: data.bunches,
						records: data.records,
						isAnonymous: data.isAnonymous,
					});
				})
				.catch(err => {
				})
				.then(() => {
					commit(MT.SET_BUNCHES_LOADING, {typeBunches, loading: false});
				});
		},

		[AC.FETCH_BUNCH_FILTER_CATALOGUES]({commit}) {
			Promise.all([
				http.get('/configuration/exchanges'),
				http.get('/configuration/assets'),
				http.get('/configuration/currencies'),
				http.get('/transfer/all-data'),
			]).then(([{data: exchanges}, {data: assets}, {data: currencies}, {data: countries}]) => {
				commit(MT.SET_CATALOGUE_EXCHANGES, exchanges);
				commit(MT.SET_CATALOGUE_ASSETS, assets);
				commit(MT.SET_CATALOGUE_COUNTRIES, countries);
				commit(MT.SET_CATALOGUE_PURCHASE_METHODS, [
					{id: 'TM', title: 'TAKER-MAKER'},
					{id: 'MM', title: 'MAKER-MAKER'},
					{id: 'MT', title: 'MAKER-TAKER'},
					{id: 'TT', title: 'TAKER-TAKER'},
				]);

				const currencyList = [];
				const paymentMethods = {};
				for (let currency of currencies) {
					currencyList.push({id: currency.id, title: currency.title});
					paymentMethods[currency.id] = currency.paymentMethods;
				}
				commit(MT.SET_CATALOGUE_CURRENCIES, currencyList);
				commit(MT.SET_CATALOGUE_PAYMENT_METHODS, paymentMethods);
			});
		},

		[AC.FETCH_TARIFF_CATALOGUES]({commit}) {
			http.get('/subscription/tariffs')
				.then((res) => {
					commit(MT.SET_TARIFF_CATALOGUE, res.data);
				});
		},

		[AC.FETCH_SUBSCRIPTION_INFO]({commit, dispatch, state}) {
			http.get('/subscription/info')
				.then(({data}) => {
					commit(MT.SET_SUBSCRIPTION_INFO, data);

					if (data.promotionCode && state.routeQuery.promoCode &&
						(data.promotionCode.id !== state.routeQuery.promoCode)) {
						dispatch(AC.APPLY_PROMOCODE, state.routeQuery.promoCode)
							.then(({data}) => {
								useMessage(data, 'success');
							});
					}
				});
		},

		[AC.FETCH_RECAPTCHA_KEY]({commit}) {
			http.get('/recaptcha-key').then((res) => {
				commit(MT.SET_RECAPTCHA_KEY, res.data);
			});
		},

		[AC.APPLY_PROMOCODE]({commit}, promoCodeId) {
			return new Promise((resolve, reject) => {
				http.get('/subscription/promocode/apply', {params: {promoCodeId}})
					.then(res => resolve(res))
					.catch(err => reject(err));
			});
		},
	},

	mutations: {
		[MT.SET_LOADING](state, loading) {
			if (loading) {
				state.loader.count++;
				state.loader.isLoading = loading;
			} else if (state.loader.count > 0) {
				state.loader.count--;
				state.loader.isLoading = state.loader.count > 0;
			}
		},

		[MT.SET_BUNCHES_LOADING](state, {typeBunches, loading}) {
			state[typeBunches].bunchesLoading = loading;
		},

		[MT.SET_USER](state, user) {
			state.user.username = user.username;
			state.user.regDate = user.regDate;
			state.user.role = user.role;
		},

		[MT.SET_ROLE](state, role) {
			state.user.role = role;
		},

		[MT.SET_SCREEN_SIZE](state, screen) {
			state.screen.width = screen.width;
			state.screen.height = screen.height;
		},

		[MT.SET_AUTH_STATUS](state, isAuth) {
			state.user.isAuth = isAuth;
		},

		[MT.SET_AUTHORIZED_USER](state, user) {
			state.user.username = user.username;
			state.user.rememberMe = user.rememberMe;
			state.user.authTime = user.authTime;
		},

		[MT.SET_ROUTE_QUERY](state, routeQuery) {
			const partner = ['site', 'partner', 'TG', 'YouTube'].find(name => (routeQuery[name]));

			state.routeQuery.partner = partner ? routeQuery[partner] : null;
			state.routeQuery.promoCode = routeQuery.promo;
		},

		[MT.SET_BUNCHES_FILTER](state, {typeBunches, newFilter}) {
			const savedFilters = state[typeBunches].filterForm;
			let latestFilter;
			for (const key in newFilter) {
				latestFilter = newFilter[key];
				if (Array.isArray(latestFilter)) {
					savedFilters[key] = [...latestFilter];
				} else if (latestFilter.length >= 0) {
					savedFilters[key] = latestFilter;
				}
			}

			if (savedFilters.isFirstFilterLoading) {
				savedFilters.isFirstFilterLoading = false;
			}
		},

		[MT.SET_DEFAULT_BUNCHES_FILTER](state, {typeBunches, usedRole, usedFilter}) {
			state[typeBunches].usedDefaultFilter.usedRole = usedRole;
			state[typeBunches].usedDefaultFilter.usedFilter = usedFilter;
		},

		[MT.SET_BUNCHES](state, {typeBunches, bunches, records, isAnonymous}) {
			state[typeBunches].bunches = bunches;
			state[typeBunches].records = records;
			state[typeBunches].isAnonymous = isAnonymous;
			state[typeBunches].bunchesUpdated = Date.now();
		},

		[MT.SET_SORTED_BUNCHES](state, {typeBunches, bunches}) {
			state[typeBunches].bunches = bunches;
		},

		[MT.SET_CATALOGUE_EXCHANGES](state, exchanges) {
			state.bunchesPage.catalogues.exchanges = exchanges;
		},

		[MT.SET_CATALOGUE_ASSETS](state, assets) {
			state.bunchesPage.catalogues.assets = assets;
		},

		[MT.SET_CATALOGUE_CURRENCIES](state, currencies) {
			state.bunchesPage.catalogues.currencies = currencies;
		},

		[MT.SET_CATALOGUE_PAYMENT_METHODS](state, paymentMethods) {
			state.bunchesPage.catalogues.paymentMethods = paymentMethods;
		},

		[MT.SET_CATALOGUE_PURCHASE_METHODS](state, purchaseMethods) {
			state.bunchesPage.catalogues.purchaseMethods = purchaseMethods;
		},

		[MT.SET_CATALOGUE_COUNTRIES](state, countries) {
			state.bunchesPage.catalogues.countries = countries;
		},

		[MT.SET_TARIFF_CATALOGUE](state, tariffs) {
			state.tariff.catalogues = tariffs;
		},

		[MT.SET_SUBSCRIPTION_INFO](state, subsc) {
			state.subscription.expiredDate = subsc.subscriptionExpiredDate;
			state.subscription.subscriptionDate = subsc.subscriptionDate;
			state.subscription.payments = subsc.payments;
			state.promoCode.id = subsc.promotionCode ? subsc.promotionCode.id : '';
			state.promoCode.tariffs = subsc.promotionCode ? subsc.promotionCode.tariffs : '';
			state.promoCode.expired = subsc.promotionCode ? subsc.promotionCode.expiredDate : '';
			state.promoCode.discountType = subsc.promotionCode ? subsc.promotionCode.discount.discountType : '';
			state.promoCode.discountValue = subsc.promotionCode ? subsc.promotionCode.discount.discountValue : '';
		},

		[MT.SET_RECAPTCHA_KEY](state, recaptchaKey) {
			state.recaptchaKey = recaptchaKey;
		},

	}
});

store.subscribe((mutation, state) => {
	const allowedMutations = [MT.SET_AUTHORIZED_USER, MT.SET_BUNCHES_FILTER];

	if (!allowedMutations.find(mt => mt === mutation.type)) {
		return;
	}

	// If you change localStorage, then do not forget to increase the currentVersion.
	const store = {
		localStorageVersion: state.localStorageVersion || state.currentVersion,
		user: {
			username: state.user.username,
			role: state.user.role,
			rememberMe: state.user.rememberMe,
			authTime: state.user.authTime,
		},
		bunchesPage: {},
		classic3BunchesPage: {},
		intBunchesPage: {},
	};

	let filterForm = state.bunchesPage.filterForm;
	store.bunchesPage.filterForm = filterForm;
	filterForm = state.classic3BunchesPage.filterForm;
	store.classic3BunchesPage.filterForm = filterForm;
	filterForm = state.intBunchesPage.filterForm;
	store.intBunchesPage.filterForm = filterForm;

	let usedDefaultFilter = state.bunchesPage.usedDefaultFilter;
	store.bunchesPage.usedDefaultFilter = usedDefaultFilter;
	usedDefaultFilter = state.classic3BunchesPage.usedDefaultFilter;
	store.classic3BunchesPage.usedDefaultFilter = usedDefaultFilter;
	usedDefaultFilter = state.intBunchesPage.usedDefaultFilter;
	store.intBunchesPage.usedDefaultFilter = usedDefaultFilter;

	localStorage.setItem('store', JSON.stringify(store));
});