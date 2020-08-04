let gulp = require('gulp');
let sass = require('gulp-sass');
let sourcemaps = require('gulp-sourcemaps');

const appPath = './src/main/resources/static/app';

gulp.task('sass-compile', () => {
    return gulp.src(appPath + '/scss/**/*.scss')
        .pipe(sourcemaps.init())
        .pipe(sass().on('error', sass.logError))
        .pipe(sourcemaps.write("./map/"))
        .pipe(gulp.dest(appPath + '/css'))
});

gulp.task('watch', () => {
    gulp.watch(appPath + "/scss/**/*.scss", gulp.series('sass-compile'));
})