UPDATE transportation_route route
SET route.is_per_ticket_pack = 0,
route.is_pin = 0,
upper_limit_weight = (
	SELECT
		upper_limit_weight
	FROM
		route_price rp
	WHERE
		rp.route_id = route.id
		AND rp.STATUS = 1
		AND rp.upper_limit_weight IS NOT NULL
		LIMIT 0,
		1
	),
	route.first_length = (
	SELECT
		first_length
	FROM
		route_price rp
	WHERE
		rp.route_id = route.id
		AND rp.STATUS = 1
		AND rp.first_length IS NOT NULL
		LIMIT 0,
		1
	),
	route.second_length = (
	SELECT
		second_length
	FROM
		route_price rp
	WHERE
		rp.route_id = route.id
		AND rp.STATUS = 1
		AND rp.second_length IS NOT NULL
		LIMIT 0,
		1
	),
	route.sensitive_price = (
	SELECT
		sensitive_price
	FROM
		route_price rp
	WHERE
		rp.route_id = route.id
		AND rp.STATUS = 1
		AND rp.sensitive_price IS NOT NULL
		LIMIT 0,
		1
	),
	route.greater_weight = (
	SELECT
		greater_weight
	FROM
		route_price rp
	WHERE
		rp.route_id = route.id
		AND rp.STATUS = 1
		AND rp.greater_weight IS NOT NULL
		LIMIT 0,
		1
	),
	route.forklift_fee = (
	SELECT
		forklift_fee
	FROM
		route_price rp
	WHERE
		rp.route_id = route.id
		AND rp.STATUS = 1
		AND rp.forklift_fee IS NOT NULL
		LIMIT 0,
		1
	),
	route.before_weight_carry_type = (
	SELECT
		weight_carry_type
	FROM
		route_price rp
	WHERE
		rp.route_id = route.id
		AND rp.STATUS = 1
		AND rp.weight_carry_type IS NOT NULL
		LIMIT 0,
		1
	),
	route.cube_upper = (
	SELECT
		cube_upper
	FROM
		route_price rp
	WHERE
		rp.route_id = route.id
		AND rp.STATUS = 1
		AND rp.cube_upper IS NOT NULL
		LIMIT 0,
		1
	),
	route.vol_conversion = (
	SELECT
		vol_conversion
	FROM
		route_price rp
	WHERE
		rp.route_id = route.id
		AND rp.STATUS = 1
		AND rp.vol_conversion IS NOT NULL
		LIMIT 0,
		1
	);



-- 查询同步的数据是否相同
SELECT
	route.upper_limit_weight,
	( SELECT upper_limit_weight FROM route_price rp WHERE rp.route_id = route.id AND rp.STATUS = 1 LIMIT 0, 1 ) AS rp_upper_limit_weight,
	route.first_length,
	( SELECT first_length FROM route_price rp WHERE rp.route_id = route.id AND rp.STATUS = 1 LIMIT 0, 1 ) AS rp_first_length,
	route.second_length,
	( SELECT second_length FROM route_price rp WHERE rp.route_id = route.id AND rp.STATUS = 1 LIMIT 0, 1 ) AS rp_second_length,
	route.sensitive_price,
	( SELECT sensitive_price FROM route_price rp WHERE rp.route_id = route.id AND rp.STATUS = 1 LIMIT 0, 1 ) AS rp_sensitive_price,
	route.greater_weight,
	( SELECT greater_weight FROM route_price rp WHERE rp.route_id = route.id AND rp.STATUS = 1 LIMIT 0, 1 ) AS rp_greater_weight,
	route.forklift_fee,
	( SELECT forklift_fee FROM route_price rp WHERE rp.route_id = route.id AND rp.STATUS = 1 LIMIT 0, 1 ) AS rp_forklift_fee,
	route.before_weight_carry_type,
	( SELECT weight_carry_type FROM route_price rp WHERE rp.route_id = route.id AND rp.STATUS = 1 LIMIT 0, 1 ) AS rp_weight_carry_type,
	route.cube_upper,
	( SELECT cube_upper FROM route_price rp WHERE rp.route_id = route.id AND rp.STATUS = 1 LIMIT 0, 1 ) AS rp_cube_upper,
	route.vol_conversion,
	( SELECT vol_conversion FROM route_price rp WHERE rp.route_id = route.id AND rp.STATUS = 1 LIMIT 0, 1 ) AS rp_vol_conversion
FROM
	transportation_route route;